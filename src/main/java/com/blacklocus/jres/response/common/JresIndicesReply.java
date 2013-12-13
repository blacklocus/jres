package com.blacklocus.jres.response.common;

import com.blacklocus.jres.model.Indices;
import com.blacklocus.jres.response.JresJsonReply;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresIndicesReply extends JresJsonReply {

    private Boolean ok;

    @JsonProperty("_indices")
    private Indices indices;

    public Boolean getOk() {
        return ok;
    }

    public Indices getIndices() {
        return indices;
    }
}
