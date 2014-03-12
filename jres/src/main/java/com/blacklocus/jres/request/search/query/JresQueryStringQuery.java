/**
 * Copyright 2013 BlackLocus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
