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
public class JresRetrieveAliasesTest extends JresTest {

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
