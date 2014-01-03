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
package com.blacklocus.jres.request;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.Jres;
import com.blacklocus.jres.request.index.JresDeleteIndex;
import com.blacklocus.jres.response.common.JresAcknowledgedReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public final class JresTest extends BaseJresTest { // final to prevent accidentally extending instead of BaseJresTest

    @Test(expected = JresErrorReplyException.class)
    public void testExcept() {
        jres.quest(new JresDeleteIndex("JresTest.testExcept".toLowerCase()));
    }

    @Test
    public void testTolerate() {
        String index = "JresTest.testExcept".toLowerCase();
        Integer toleratedStatus = HttpStatus.SC_NOT_FOUND;

        Jres.Tolerance<JresAcknowledgedReply> tolerance = jres.tolerate(new JresDeleteIndex(index), toleratedStatus);
        Assert.assertTrue(tolerance.isError());
        Assert.assertNull(tolerance.getReply());
        Assert.assertNotNull(tolerance.getError());
        Assert.assertEquals(toleratedStatus, tolerance.getError().getStatus());
    }
}
