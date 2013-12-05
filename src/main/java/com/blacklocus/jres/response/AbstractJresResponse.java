package com.blacklocus.jres.response;

import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.str.ObjectMappers;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;

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

    @Override
    public String toString() {
        try {
            return ObjectMappers.PRETTY.writeValueAsString(asNode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
