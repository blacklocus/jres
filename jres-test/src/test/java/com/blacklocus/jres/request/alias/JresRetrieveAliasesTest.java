/**
 * Copyright 2015 BlackLocus
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
package com.blacklocus.jres.request.alias;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.response.alias.JresRetrieveAliasesReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class JresRetrieveAliasesTest extends BaseJresTest {

    @Test(expected = JresErrorReplyException.class)
    public void sad() {
        String index = "JresRetrieveAliasesRequestTest".toLowerCase();
        String alias = index + "_alias";

        // no matches returns 404 response
        jres.quest(new JresRetrieveAliases(index, alias));
    }

    @Test
    public void happy() {
        String index = "JresRetrieveAliasesRequestTest_happy".toLowerCase();
        String alias = index + "_alias";

        jres.quest(new JresCreateIndex(index));
        jres.quest(new JresAddAlias(index, alias));

        JresRetrieveAliasesReply response = jres.quest(new JresRetrieveAliases(index, "*"));
        Assert.assertEquals(Arrays.asList(index), response.getIndexes());
        Assert.assertEquals(Arrays.asList(alias), response.getAliases(index));
    }
}
