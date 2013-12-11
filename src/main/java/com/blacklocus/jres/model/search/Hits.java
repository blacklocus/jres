package com.blacklocus.jres.model.search;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
* @author Jason Dunkelberger (dirkraft)
*/
public class Hits {

    private Integer total;

    @JsonProperty("max_score")
    private Double maxScore;

    private List<Hit> hits;

    public Integer getTotal() {
        return total;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public List<Hit> getHits() {
        return hits;
    }
}
