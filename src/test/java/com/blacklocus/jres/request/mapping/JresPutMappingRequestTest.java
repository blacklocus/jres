package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.index.JresCreateIndexRequest;
import com.blacklocus.jres.response.JresBooleanResponse;
import com.blacklocus.jres.response.common.JresAcknowledgedResponse;
import com.blacklocus.jres.response.common.JresErrorResponseException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresPutMappingRequestTest extends JresTest {

    @Test
    public void sad() {
        String index = "JresPutMappingRequestTest_sad".toLowerCase();
        String type = "test";

        try {
            jres.request(new JresPutMappingRequest(index, type, "{\"test\":{}}"));
            Assert.fail("Shouldn't be able to put type mapping on non-existent index");
        } catch (JresErrorResponseException e) {
            // good
        }

        jres.request(new JresCreateIndexRequest(index));

        try {
            jres.request(new JresPutMappingRequest(index, type, null));
            Assert.fail("Invalid data");
        } catch (JresErrorResponseException e) {
            // good
        }

        try {
            jres.request(new JresPutMappingRequest(index, type, ""));
            Assert.fail("Invalid data");
        } catch (JresErrorResponseException e) {
            // good
        }

        try {
            jres.request(new JresPutMappingRequest(index, type, "{}"));
            Assert.fail("Invalid data");
        } catch (JresErrorResponseException e) {
            // good
        }
    }

    @Test
    public void happy() {
        String index = "JresPutMappingRequestTest_happy".toLowerCase();
        String type = "test";

        {
            JresBooleanResponse response = jres.request(new JresTypeExistsRequest(index, type));
            Assert.assertFalse(response.verity());
        }

        jres.request(new JresCreateIndexRequest(index));
        {
            JresAcknowledgedResponse response = jres.request(new JresPutMappingRequest(index, type, "{\"test\":{}}"));
            Assert.assertTrue(response.isOk() && response.isAcknowledged());
        }

        {
            JresBooleanResponse response = jres.request(new JresTypeExistsRequest(index, type));
            Assert.assertTrue(response.verity());
        }
    }
}
