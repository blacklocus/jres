package com.blacklocus.jres.response.common;

import com.blacklocus.jres.request.alias.JresAddAlias;
import com.blacklocus.jres.request.alias.JresDeleteAlias;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.request.index.JresGetIndexSettings;
import com.blacklocus.jres.request.mapping.JresGetMapping;
import com.blacklocus.jres.request.mapping.JresPutMapping;
import com.blacklocus.jres.response.JresReply;
import org.codehaus.jackson.JsonNode;

/**
 * Possible response for <ul>
 * <li>{@link JresCreateIndex}</li>
 * <li>{@link JresGetIndexSettings}</li>
 * <li>{@link JresPutMapping}</li>
 * <li>{@link JresGetMapping}</li>
 * <li>{@link JresAddAlias}</li>
 * <li>{@link JresDeleteAlias}</li>
 * </ul>
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresErrorReplyException extends RuntimeException implements JresReply {

    private String error;
    private Integer status;

    private JsonNode node;

    @Override
    public JsonNode node() {
        return node;
    }

    public JresErrorReplyException node(JsonNode node) {
        this.node = node;
        return this;
    }

    public String getError() {
        return error;
    }

    public Integer getStatus() {
        return status;
    }
}
