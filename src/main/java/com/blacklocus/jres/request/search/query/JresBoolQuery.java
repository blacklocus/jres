package com.blacklocus.jres.request.search.query;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresBoolQuery implements JresQuery {

    /** Each map is a single entry from {@link JresQuery#queryType()} to the JresQuery itself */
    private List<Map<String, JresQuery>> should;
    @JsonProperty("minimum_should_match")
    private Integer minimumShouldMatch;
    private Double boost;

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serializable getter properties

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
