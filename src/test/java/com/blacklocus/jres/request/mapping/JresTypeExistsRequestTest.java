package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.index.JresCreateIndexRequest;
import com.blacklocus.jres.response.JresBooleanResponse;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresTypeExistsRequestTest extends JresTest {

    @Test
    public void happy() {
        String index = "JresTypeExistsRequestTest_happy".toLowerCase();
        String type = "test";

        jres.request(new JresCreateIndexRequest(index));
        jres.request(new JresPutMappingRequest(index, type));

        JresBooleanResponse response = jres.request(new JresTypeExistsRequest(index, type));
        Assert.assertTrue(response.verity());
    }

    @Test
    public void sad() {
        String index = "JresTypeExistsRequestTest_sad".toLowerCase();
        String type = "test";

        JresBooleanResponse response = jres.request(new JresTypeExistsRequest(index, type));
        Assert.assertFalse(response.verity());

        jres.request(new JresCreateIndexRequest(index));
        response = jres.request(new JresTypeExistsRequest(index, type));
        Assert.assertFalse(response.verity());
    }
}
