package com.blacklocus.jres.model.search;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class Hit<H extends Hit<H>> {

    @JsonProperty("_index")
    private String index;
    @JsonProperty("_type")
    private String type;
    @JsonProperty("_id")
    private String id;
    @JsonProperty("_score")
    private Double score;
    @JsonProperty("_source")
    private H source;


    public String getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Double getScore() {
        return score;
    }

    public H getSource() {
        return source;
    }
}
