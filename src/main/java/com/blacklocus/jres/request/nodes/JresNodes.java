package com.blacklocus.jres.request.nodes;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.JresJsonReply;
import org.apache.http.client.methods.HttpGet;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/cluster-nodes-info.html#cluster-nodes-info">Nodes API</a>
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresNodes extends JresJsonRequest<JresJsonReply> {

    public JresNodes() {
        super(JresJsonReply.class);
    }

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

}
