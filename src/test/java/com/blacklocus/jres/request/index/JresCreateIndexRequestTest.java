package com.blacklocus.jres.request.index;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.response.JresBooleanResponse;
import com.blacklocus.jres.response.common.JresErrorResponseException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresCreateIndexRequestTest extends JresTest {

    @Test(expected = JresErrorResponseException.class)
    public void sad() {
        // index names must be lowercase
        String indexName = "JresCreateIndexRequestTest_sad";

        JresBooleanResponse indexExistsResponse = jres.request(new JresIndexExistsRequest(indexName));
        Assert.assertFalse(indexExistsResponse.verity());

        jres.request(new JresCreateIndexRequest(indexName));
    }

    @Test
    public void happy() {
        String indexName = "JresCreateIndexRequestTest_happy".toLowerCase();

        JresBooleanResponse indexExistsResponse = jres.request(new JresIndexExistsRequest(indexName));
        Assert.assertFalse(indexExistsResponse.verity());

        jres.request(new JresCreateIndexRequest(indexName));
        indexExistsResponse = jres.request(new JresIndexExistsRequest(indexName));
        Assert.assertTrue(indexExistsResponse.verity());
    }
}
