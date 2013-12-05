package com.blacklocus.jres.response;

import com.blacklocus.jres.request.JresRequest;
import org.codehaus.jackson.JsonNode;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresNodesResponse extends AbstractJresResponse {

    public JresNodesResponse(JresRequest<?> request, JsonNode node) {
        super(request, node);
    }
}
