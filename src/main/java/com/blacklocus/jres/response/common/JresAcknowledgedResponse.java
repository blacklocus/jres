package com.blacklocus.jres.response.common;

import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.request.index.JresCreateIndexRequest;
import com.blacklocus.jres.response.AbstractJresResponse;
import org.codehaus.jackson.JsonNode;

/**
 * Possible response for <ul>
 * <li>{@link JresCreateIndexRequest}</li>
 * </ul>
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresAcknowledgedResponse extends AbstractJresResponse<JsonNode> {

    private final boolean ok;
    private final boolean acknowledged;

    public JresAcknowledgedResponse(JresRequest<JsonNode, ?> request, JsonNode jsonNode) {
        super(jsonNode);

        this.ok = jsonNode.get("ok").asBoolean();
        this.acknowledged = jsonNode.get("acknowledged").asBoolean();
    }

    public boolean isOk() {
        return ok;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }
}
