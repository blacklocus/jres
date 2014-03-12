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
package com.blacklocus.jres.request.index;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.response.JresBooleanReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.junit.Assert;
import org.junit.Test;

public class JresCreateIndexTest extends BaseJresTest {

    @Test(expected = JresErrorReplyException.class)
    public void sad() {
        // index names must be lowercase
        String indexName = "JresCreateIndexRequestTest_sad";

        JresBooleanReply indexExistsResponse = jres.bool(new JresIndexExists(indexName));
        Assert.assertFalse(indexExistsResponse.verity());

        jres.quest(new JresCreateIndex(indexName));
    }

    @Test
    public void happy() {
        String indexName = "JresCreateIndexRequestTest_happy".toLowerCase();

        JresBooleanReply indexExistsResponse = jres.bool(new JresIndexExists(indexName));
        Assert.assertFalse(indexExistsResponse.verity());

        jres.quest(new JresCreateIndex(indexName));
        indexExistsResponse = jres.bool(new JresIndexExists(indexName));
        Assert.assertTrue(indexExistsResponse.verity());
    }
}
