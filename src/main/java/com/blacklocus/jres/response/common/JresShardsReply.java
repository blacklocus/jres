package com.blacklocus.jres.response.common;

import com.blacklocus.jres.model.Shards;
import com.blacklocus.jres.response.JresJsonReply;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresShardsReply extends JresJsonReply {

    private Boolean ok;

    @JsonProperty("_shards")
    private Shards shards;

    public Boolean isOk() {
        return ok;
    }

    public Shards getShards() {
        return shards;
    }
}
