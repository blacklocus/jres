package com.blacklocus.jres.model.search;

import org.codehaus.jackson.annotate.JsonProperty;

public class Facet {

    @JsonProperty("_type")
    private String type;

    private Long missing;
    private Long total;
    private Long other;

    public String getType() {
        return type;
    }

    public Long getMissing() {
        return missing;
    }

    public Long getTotal() {
        return total;
    }

    public Long getOther() {
        return other;
    }
}
