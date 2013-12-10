package com.blacklocus.jres.response;

import com.blacklocus.jres.strings.ObjectMappers;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresJsonReply implements JresReply {

    private JsonNode node;

    @Override
    public JsonNode node() {
        return node;
    }

    public JresJsonReply node(JsonNode node) {
        this.node = node;
        return this;
    }

    @Override
    public String toString() {
        try {
            return ObjectMappers.PRETTY.writeValueAsString(node());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
