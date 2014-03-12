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
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JresBoolQuery implements JresQuery {

    /** Each map is a single entry from {@link JresQuery#queryType()} to the JresQuery itself */
    private List<Map<String, JresQuery>> must;
    @JsonProperty("must_not")
    private List<Map<String, JresQuery>> mustNot;
    private List<Map<String, JresQuery>> should;
    @JsonProperty("minimum_should_match")
    private Integer minimumShouldMatch;
    private Double boost;

    public JresBoolQuery must(JresQuery... must) {
        return must(Arrays.asList(must));
    }

    public JresBoolQuery must(List<JresQuery> must) {
        this.must = Lists.transform(must, new Function<JresQuery, Map<String, JresQuery>>() {
            @Override
            public Map<String, JresQuery> apply(JresQuery input) {
                return ImmutableMap.of(input.queryType(), input);
            }
        });
        return this;
    }

    public JresBoolQuery mustNot(JresQuery... must) {
        return mustNot(Arrays.asList(must));
    }

    public JresBoolQuery mustNot(List<JresQuery> mustNot) {
        this.mustNot = Lists.transform(mustNot, new Function<JresQuery, Map<String, JresQuery>>() {
            @Override
            public Map<String, JresQuery> apply(JresQuery input) {
                return ImmutableMap.of(input.queryType(), input);
            }
        });
        return this;
    }

    public JresBoolQuery should(JresQuery... should) {
        return should(Arrays.asList(should));
    }

    public JresBoolQuery should(List<JresQuery> should) {
        this.should = Lists.transform(should, new Function<JresQuery, Map<String, JresQuery>>() {
            @Override
            public Map<String, JresQuery> apply(JresQuery input) {
                return ImmutableMap.of(input.queryType(), input);
            }
        });
        return this;
    }

    public JresBoolQuery minimumShouldMatch(Integer minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
        return this;
    }

    public JresBoolQuery boost(Double boost) {
        this.boost = boost;
        return this;
    }

    @Override
    public String queryType() {
        return "bool";
    }

    @Override
    public String toString() {
        return ObjectMappers.toJson(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serializable getter properties


    public List<Map<String, JresQuery>> getMust() {
        return must;
    }

    public List<Map<String, JresQuery>> getMustNot() {
        return mustNot;
    }

    public List<Map<String, JresQuery>> getShould() {
        return should;
    }

    public Integer getMinimumShouldMatch() {
        return minimumShouldMatch;
    }

    public Double getBoost() {
        return boost;
    }
}
