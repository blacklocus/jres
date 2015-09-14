/**
 * Copyright 2015 BlackLocus
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
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
import com.blacklocus.jres.response.common.JresAcknowledgedReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class JresDeleteAliasTest extends BaseJresTest {

    @Test
    public void happy() {
        String index = "JresDeleteAliasRequestTest_happy".toLowerCase();
        String alias = index + "_alias";

        jres.quest(new JresCreateIndex(index));

        // Delete alias excepts if the alias doesn't exist.
        jres.tolerate(new JresDeleteAlias(index, alias), 404);

        jres.quest(new JresAddAlias(index, alias));
        JresRetrieveAliasesReply retrieveAliasesResponse = jres.quest(new JresRetrieveAliases(index, alias));
        Assert.assertEquals(Collections.singletonList(alias), retrieveAliasesResponse.getAliases(index));

        JresAcknowledgedReply response = jres.quest(new JresDeleteAlias(index, alias));
        Assert.assertTrue(response.getAcknowledged());

        retrieveAliasesResponse = jres.quest(new JresRetrieveAliases(index, "*"));
        Assert.assertEquals(0, retrieveAliasesResponse.getAliases(index).size());
    }

    @Test(expected = JresErrorReplyException.class)
    public void sad() {
        String index = "JresDeleteAliasRequestTest_sad".toLowerCase();
        String alias = index + "_alias";

        // index doesn't even exist
        jres.quest(new JresDeleteAlias(index, alias));
    }
}
