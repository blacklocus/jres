package com.blacklocus.jres.response.common;

import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.request.mapping.JresPutMapping;
import com.blacklocus.jres.response.JresJsonReply;

/**
 * Possible response for <ul>
 * <li>{@link JresCreateIndex}</li>
 * <li>{@link JresPutMapping}</li>
 * </ul>
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresAcknowledgedReply extends JresJsonReply {

    private boolean ok;
    private boolean acknowledged;

    public boolean isOk() {
        return ok;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }
}
