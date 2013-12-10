package com.blacklocus.jres.response.search;

import com.blacklocus.jres.response.JresJsonReply;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresSearchReply extends JresJsonReply {

    private int took;
    @JsonProperty("timed_out")
    private boolean timedOut;

    public int getTook() {
        return took;
    }

    public boolean isTimedOut() {
        return timedOut;
    }
}
