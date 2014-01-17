package com.blacklocus.jres.request.search.facet;

import org.codehaus.jackson.annotate.JsonProperty;

public class JresTermsFacet implements JresFacet {

    private String name;

    private String field;
    @JsonProperty("all_terms")
    private Boolean allTerms;
    private String regex;

    public JresTermsFacet(String name, String field) {
        this.name = name;
        this.field = field;
    }

    @Override
    public String facetName() {
        return name;
    }

    @Override
    public String facetType() {
        return "terms";
    }

    public JresTermsFacet allTerms(Boolean allTerms) {
        this.allTerms = allTerms;
        return this;
    }

    public JresTermsFacet regex(String regex) {
        this.regex = regex;
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serializable getter properties

    public String getField() {
        return field;
    }

    public Boolean getAllTerms() {
        return allTerms;
    }

    public String getRegex() {
        return regex;
    }
}
