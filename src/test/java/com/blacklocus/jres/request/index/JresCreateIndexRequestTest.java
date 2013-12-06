package com.blacklocus.jres.request.index;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.response.common.JresAcknowledgedResponse;
import com.blacklocus.jres.response.common.JresErrorResponseException;
import com.blacklocus.jres.response.index.JresIndexExistsResponse;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresCreateIndexRequestTest extends JresTest {

    @Test
    public void testError() {
        // index names must be lowercase
        String indexName = "JresCreateIndexRequestTest";

        JresIndexExistsResponse indexExistsResponse = jres.request(new JresIndexExistsRequest(indexName));
        Assert.assertFalse(indexExistsResponse.indexExists());

        try {
            jres.request(new JresCreateIndexRequest(indexName));
            Assert.fail();
        } catch (JresErrorResponseException e) {
            // good
        }
    }

    @Test
    public void testSuccess() {
        String indexName = "JresCreateIndexRequestTest".toLowerCase();

        JresIndexExistsResponse indexExistsResponse = jres.request(new JresIndexExistsRequest(indexName));
        Assert.assertFalse(indexExistsResponse.indexExists());

        JresAcknowledgedResponse createIndexResponse = jres.request(new JresCreateIndexRequest(indexName));
        Assert.assertTrue(createIndexResponse.isOk() && createIndexResponse.isAcknowledged());

        indexExistsResponse = jres.request(new JresIndexExistsRequest(indexName));
        Assert.assertTrue(indexExistsResponse.indexExists());
    }
}
