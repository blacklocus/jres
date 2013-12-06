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

    @Test
    public void testSad() {
        // index names must be lowercase
        String indexName = "JresCreateIndexRequestTest";

        JresBooleanResponse indexExistsResponse = jres.request(new JresIndexExistsRequest(indexName));
        Assert.assertFalse(indexExistsResponse.verity());

        try {
            jres.request(new JresCreateIndexRequest(indexName));
            Assert.fail();
        } catch (JresErrorResponseException e) {
            // good
        }
    }

    @Test
    public void testHappy() {
        String indexName = "JresCreateIndexRequestTest".toLowerCase();

        JresBooleanResponse indexExistsResponse = jres.request(new JresIndexExistsRequest(indexName));
        Assert.assertFalse(indexExistsResponse.verity());

        jres.request(new JresCreateIndexRequest(indexName));
        indexExistsResponse = jres.request(new JresIndexExistsRequest(indexName));
        Assert.assertTrue(indexExistsResponse.verity());
    }
}
