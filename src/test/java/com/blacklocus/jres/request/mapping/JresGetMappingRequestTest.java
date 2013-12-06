package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.index.JresCreateIndexRequest;
import com.blacklocus.jres.response.common.JresErrorResponseException;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresGetMappingRequestTest extends JresTest {

    @Test(expected = JresErrorResponseException.class)
    public void sad() {
        String index = "JresGetMappingRequestTest_sad".toLowerCase();
        String type = "test";

        jres.request(new JresGetMappingRequest(index, type));
    }

    @Test
    public void happy() {
        String index = "JresGetMappingRequestTest_happy".toLowerCase();
        String type = "test";

        jres.request(new JresCreateIndexRequest(index));
        jres.request(new JresPutMappingRequest(index, type));

        jres.request(new JresGetMappingRequest(index, type));
    }
}
