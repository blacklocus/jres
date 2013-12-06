package com.blacklocus.jres.response.common;

import com.blacklocus.jres.request.index.JresCreateIndexRequest;
import com.blacklocus.jres.response.JresResponse;
import org.codehaus.jackson.JsonNode;

/**
 * Possible response for <ul>
 * <li>{@link JresCreateIndexRequest}</li>
 * </ul>
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresErrorResponseException extends RuntimeException implements JresResponse<JsonNode> {

    private final JsonNode node;

    private final String error;
    private final int status;

    public JresErrorResponseException(JsonNode jsonNode) {
        super(jsonNode.get("error").asText());
        this.node = jsonNode;

        this.error = jsonNode.get("error").asText();
        this.status = jsonNode.get("status").asInt();
    }

    @Override
    public JsonNode basis() {
        return node;
    }

    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }
}
