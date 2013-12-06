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
public class JresAddAliasRequestTest extends JresTest {

    @Test
    public void happy() {
        String index = "JresAddAliasRequestTest_happy".toLowerCase();
        String alias = index + "_alias";

        jres.request(new JresCreateIndexRequest(index));

        jres.request(new JresAddAliasRequest(index, alias));
        JresRetrieveAliasesResponse retrieveAliasesResponse = jres.request(new JresRetrieveAliasesRequest(index, alias));
        Assert.assertEquals(Arrays.asList(alias), retrieveAliasesResponse.getAliases(index));
    }

    @Test(expected = JresErrorResponseException.class)
    public void sad() {
        String index = "JresAddAliasRequestTest_sad".toLowerCase();
        String alias = index + "_alias";

        // index doesn't even exist
        jres.request(new JresAddAliasRequest(index, alias));
    }

}
