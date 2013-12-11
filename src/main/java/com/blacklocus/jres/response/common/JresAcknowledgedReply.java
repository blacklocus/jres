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

    private Boolean ok;
    private Boolean acknowledged;

    public Boolean getOk() {
        return ok;
    }

    public Boolean isAcknowledged() {
        return acknowledged;
    }
}
