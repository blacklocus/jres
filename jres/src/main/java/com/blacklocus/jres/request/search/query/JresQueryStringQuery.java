package com.blacklocus.jres.request.search.query;

import com.blacklocus.jres.strings.ObjectMappers;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JresQueryStringQuery implements JresQuery {

    private String query;
    @JsonProperty("default_field")
    private String defaultField;
    @JsonProperty("default_operator")
    private String defaultOperator;

    public JresQueryStringQuery() {
    }

    public JresQueryStringQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public JresQueryStringQuery withQuery(String query) {
        this.query = query;
        return this;
    }

    public String getDefaultField() {
        return defaultField;
    }

    public void setDefaultField(String defaultField) {
        this.defaultField = defaultField;
    }

    public JresQueryStringQuery withDefaultField(String defaultField) {
        this.defaultField = defaultField;
        return this;
    }

    public String getDefaultOperator() {
        return defaultOperator;
    }

    public void setDefaultOperator(String defaultOperator) {
        this.defaultOperator = defaultOperator;
    }

    public JresQueryStringQuery withDefaultOperator(String defaultOperator) {
        this.defaultOperator = defaultOperator;
        return this;
    }

    @Override
    public String queryType() {
        return "query_string";
    }

    @Override
    public String toString() {
        return ObjectMappers.toJson(this);
    }

}
