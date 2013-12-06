package com.blacklocus.jres.request.alias;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.index.JresCreateIndexRequest;
import com.blacklocus.jres.response.alias.JresRetrieveAliasesResponse;
import com.blacklocus.jres.response.common.JresErrorResponseException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresRetrieveAliasesRequestTest extends JresTest {

    @Test(expected = JresErrorResponseException.class)
    public void sad() {
        String index = "JresRetrieveAliasesRequestTest".toLowerCase();
        String alias = index + "_alias";

        // no matches returns 404 response
        jres.request(new JresRetrieveAliasesRequest(index, alias));
    }

    @Test
    public void happy() {
        String index = "JresRetrieveAliasesRequestTest_happy".toLowerCase();
        String alias = index + "_alias";

        jres.request(new JresCreateIndexRequest(index));
        jres.request(new JresAddAliasRequest(index, alias));

        JresRetrieveAliasesResponse response = jres.request(new JresRetrieveAliasesRequest(index, "*"));
        Assert.assertEquals(Arrays.asList(index), response.getIndexes());
        Assert.assertEquals(Arrays.asList(alias), response.getAliases(index));
    }
}
