package com.blacklocus.jres.request.alias;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.index.JresCreateIndexRequest;
import com.blacklocus.jres.response.alias.JresRetrieveAliasesResponse;
import com.blacklocus.jres.response.common.JresAcknowledgedResponse;
import com.blacklocus.jres.response.common.JresErrorResponseException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresDeleteAliasRequestTest extends JresTest {

    @Test
    public void happy() {
        String index = "JresDeleteAliasRequestTest_happy".toLowerCase();
        String alias = index + "_alias";

        jres.request(new JresCreateIndexRequest(index));

        // delete doesn't seem to care whether the alias exists or not
        jres.request(new JresDeleteAliasRequest(index, alias));

        jres.request(new JresAddAliasRequest(index, alias));
        JresRetrieveAliasesResponse retrieveAliasesResponse = jres.request(new JresRetrieveAliasesRequest(index, alias));
        Assert.assertEquals(Arrays.asList(alias), retrieveAliasesResponse.getAliases(index));

        JresAcknowledgedResponse response = jres.request(new JresDeleteAliasRequest(index, alias));
        Assert.assertTrue(response.isOk() && response.isAcknowledged());
        try {
            retrieveAliasesResponse = jres.request(new JresRetrieveAliasesRequest(index, "*"));
            Assert.assertEquals(0, retrieveAliasesResponse.getAliases(index).size());
            Assert.fail("Should have failed with 404, no aliases found.");
        } catch (JresErrorResponseException e) {
            // good
        }
    }

    @Test(expected = JresErrorResponseException.class)
    public void sad() {
        String index = "JresDeleteAliasRequestTest_sad".toLowerCase();
        String alias = index + "_alias";

        // index doesn't even exist
        jres.request(new JresDeleteAliasRequest(index, alias));
    }
}
