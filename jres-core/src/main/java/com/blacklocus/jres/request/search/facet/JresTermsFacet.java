/**
 * Copyright 2015 BlackLocus
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
package com.blacklocus.jres.request.search.facet;

import com.fasterxml.jackson.annotation.JsonProperty;

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
