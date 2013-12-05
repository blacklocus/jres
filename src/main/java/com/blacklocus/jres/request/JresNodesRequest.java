package com.blacklocus.jres.request;

import com.blacklocus.jres.response.JresNodesResponse;
import com.blacklocus.jres.response.handler.JresNodesResponseHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/cluster-nodes-info.html#cluster-nodes-info">Nodes API</a>
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresNodesRequest implements JresRequest<JresNodesResponse> {

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
        return new JresNodesResponseHandler(this);
    }
}
