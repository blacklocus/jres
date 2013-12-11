package com.blacklocus.jres.response.search;

import com.blacklocus.jres.model.Shards;
import com.blacklocus.jres.model.search.Hits;
import com.blacklocus.jres.response.JresJsonReply;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresSearchReply extends JresJsonReply {

    private Integer took;

    @JsonProperty("timed_out")
    private Boolean timedOut;

    @JsonProperty("_shards")
    private Shards shards;

    private Hits hits;

    public Integer getTook() {
        return took;
    }

    public Boolean isTimedOut() {
        return timedOut;
    }

    public Shards getShards() {
        return shards;
    }

    public Hits getHits() {
        return hits;
    }
}
