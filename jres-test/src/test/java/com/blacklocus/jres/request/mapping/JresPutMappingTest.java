/**
 * Copyright 2013 BlackLocus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.response.JresBooleanReply;
import com.blacklocus.jres.response.common.JresAcknowledgedReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.junit.Assert;
import org.junit.Test;

public class JresPutMappingTest extends BaseJresTest {

    @Test
    public void sad() {
        String index = "JresPutMappingRequestTest_sad".toLowerCase();
        String type = "test";

        try {
            jres.quest(new JresPutMapping(index, type, "{\"test\":{}}"));
            Assert.fail("Shouldn't be able to put type mapping on non-existent index");
        } catch (JresErrorReplyException e) {
            // good
        }

        jres.quest(new JresCreateIndex(index));

        try {
            jres.quest(new JresPutMapping(index, type, null));
            Assert.fail("Invalid data");
        } catch (JresErrorReplyException e) {
            // good
        }

        try {
            jres.quest(new JresPutMapping(index, type, ""));
            Assert.fail("Invalid data");
        } catch (JresErrorReplyException e) {
            // good
        }

        try {
            jres.quest(new JresPutMapping(index, type, "{}"));
            Assert.fail("Invalid data");
        } catch (JresErrorReplyException e) {
            // good
        }
    }

    @Test
    public void happy() {
        String index = "JresPutMappingRequestTest_happy".toLowerCase();
        String type = "test";

        {
            JresBooleanReply response = jres.bool(new JresTypeExists(index, type));
            Assert.assertFalse(response.verity());
        }

        jres.quest(new JresCreateIndex(index));
        {
            JresAcknowledgedReply response = jres.quest(new JresPutMapping(index, type, "{\"test\":{}}"));
            Assert.assertTrue(response.getOk() && response.getAcknowledged());
        }

        {
            JresBooleanReply response = jres.bool(new JresTypeExists(index, type));
            Assert.assertTrue(response.verity());
        }
    }
}
