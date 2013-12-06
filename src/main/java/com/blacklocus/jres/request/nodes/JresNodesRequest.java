package com.blacklocus.jres.request.nodes;

import com.blacklocus.jres.handler.AbstractJsonNodeJresResponseHandler;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.JresJsonNodeResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.codehaus.jackson.JsonNode;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/cluster-nodes-info.html#cluster-nodes-info">Nodes API</a>
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresNodesRequest implements JresRequest<JsonNode, JresJsonNodeResponse> {

    @Override
    public String getHttpMethod() {
        return HttpGet.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return "_nodes";
    }

    @Override
    public Object getPayload() {
        return null;
    }

    @Override
    public ResponseHandler<JresJsonNodeResponse> getResponseHandler() {
        return new AbstractJsonNodeJresResponseHandler<JresJsonNodeResponse>() {
            @Override
            public JresJsonNodeResponse makeResponse(JsonNode value) {
                return new JresJsonNodeResponse(value);
            }
        };
    }
}
