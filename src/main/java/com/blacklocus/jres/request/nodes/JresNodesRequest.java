package com.blacklocus.jres.request.nodes;

import com.blacklocus.jres.request.AbstractJsonNodeJresResponseHandler;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.nodes.JresNodesResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.codehaus.jackson.JsonNode;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/cluster-nodes-info.html#cluster-nodes-info">Nodes API</a>
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresNodesRequest implements JresRequest<JsonNode, JresNodesResponse> {

    @Override
    public String getHttpMethod() {
        return HttpGet.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return "_nodes";
    }

    @Override
    public ResponseHandler<JresNodesResponse> getResponseHandler() {
        return new AbstractJsonNodeJresResponseHandler<JresNodesResponse>() {
            @Override
            public JresNodesResponse makeResponse(JsonNode node) {
                return new JresNodesResponse(JresNodesRequest.this, node);
            }
        };
    }
}
