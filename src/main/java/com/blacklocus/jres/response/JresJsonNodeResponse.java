package com.blacklocus.jres.response;

import com.blacklocus.jres.strings.ObjectMappers;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresJsonNodeResponse extends AbstractJresResponse<JsonNode> {

    public JresJsonNodeResponse(JsonNode jsonNode) {
        super(jsonNode);
    }

    /**
     * alias to {@link #basis()}, sometimes more descriptive in code
     */
    public JsonNode asNode() {
        return basis();
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
