package com.blacklocus.jres.request.alias;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.response.alias.JresRetrieveAliasesReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresAddAliasTest extends JresTest {

    @Test
    public void happy() {
        String index = "JresAddAliasRequestTest_happy".toLowerCase();
        String alias = index + "_alias";

        jres.quest(new JresCreateIndex(index));

        jres.quest(new JresAddAlias(index, alias));
        JresRetrieveAliasesReply retrieveAliasesReply = jres.quest(new JresRetrieveAliases(index, alias));
        Assert.assertEquals(Arrays.asList(alias), retrieveAliasesReply.getAliases(index));
    }

    @Test(expected = JresErrorReplyException.class)
    public void sad() {
        String index = "JresAddAliasRequestTest_sad".toLowerCase();
        String alias = index + "_alias";

        // index doesn't even exist
        jres.quest(new JresAddAlias(index, alias));
    }

}
