package com.blacklocus.jres.response;

import com.blacklocus.jres.request.JresRequest;
import org.codehaus.jackson.JsonNode;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public interface JresResponse {

    /**
     * @return the request that resulted in this response
     */
    JresRequest getRequest();

    /**
     * @return the underlying JsonNode which contains ALL of the response, regardless of what may have been parsed into
     * semantic, declared properties of this JresResponse
     */
    JsonNode asNode();
}
