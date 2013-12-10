package com.blacklocus.jres.model.search;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
* @author Jason Dunkelberger (dirkraft)
*/
public class Hits<H extends Hit<H>> {

    private int total;
    @JsonProperty("max_score")
    private Double maxScore;
    private List<H> hits;

    public int getTotal() {
        return total;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public List<H> getHits() {
        return hits;
    }
}
