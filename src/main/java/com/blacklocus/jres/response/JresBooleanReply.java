package com.blacklocus.jres.response;

import org.codehaus.jackson.JsonNode;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresBooleanReply implements JresReply {

    private final boolean verity;

    public JresBooleanReply(boolean verity) {
        this.verity = verity;
    }

    @Override
    public JsonNode node() {
        return null;
    }

    public boolean verity() {
        return verity;
    }

}
