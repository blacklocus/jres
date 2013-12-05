package com.blacklocus.jres.response.handler;

import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.JresNodesResponse;
import org.codehaus.jackson.JsonNode;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresNodesResponseHandler extends AbstractJresResponseHandler<JresNodesResponse> {

    public JresNodesResponseHandler(JresRequest<JresNodesResponse> request) {
        super(request);
    }

    @Override
    public JresNodesResponse makeResponse(JresRequest<JresNodesResponse> request, JsonNode node) {
        return new JresNodesResponse(request, node);
    }
}
