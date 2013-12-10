package com.blacklocus.jres.response;

import org.codehaus.jackson.JsonNode;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public interface JresReply {

    /**
     * @return response as unassuming JsonNode. <code>null</code> if the response body contained no JSON.
     */
    JsonNode node();

}
