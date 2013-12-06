package com.blacklocus.jres.response.nodes;

import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.request.nodes.JresNodesRequest;
import com.blacklocus.jres.response.AbstractJresResponse;
import com.blacklocus.jres.response.JresJsonNodeResponse;
import org.codehaus.jackson.JsonNode;

/**
 * Response for {@link JresNodesRequest}
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresNodesResponse extends JresJsonNodeResponse {

    public JresNodesResponse(JresRequest<JsonNode, ?> request, JsonNode jsonNode) {
        super(request, jsonNode);
    }
}
