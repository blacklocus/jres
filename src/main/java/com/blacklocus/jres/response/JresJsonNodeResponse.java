package com.blacklocus.jres.response;

import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.strings.ObjectMappers;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresJsonNodeResponse extends AbstractJresResponse<JsonNode> {

    public JresJsonNodeResponse(JresRequest<JsonNode, ?> request, JsonNode jsonNode) {
        super(request, jsonNode);
    }

    @Override
    public String toString() {
        try {
            return ObjectMappers.PRETTY.writeValueAsString(basis());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
