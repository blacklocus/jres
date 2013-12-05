package com.blacklocus.jres.response;

import com.blacklocus.jres.request.JresRequest;
import org.codehaus.jackson.JsonNode;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public abstract class AbstractJresResponse implements JresResponse {

    private final JresRequest<?> request;
    private final JsonNode node;

    protected AbstractJresResponse(JresRequest<?> request, JsonNode node) {
        this.request = request;
        this.node = node;
    }

    @Override
    public JresRequest<?> getRequest() {
        return request;
    }

    @Override
    public JsonNode asNode() {
        return node;
    }
}
