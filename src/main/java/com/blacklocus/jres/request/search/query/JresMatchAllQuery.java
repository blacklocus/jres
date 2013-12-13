package com.blacklocus.jres.request.search.query;

import org.codehaus.jackson.annotate.JsonValue;

import java.util.Collections;
import java.util.Map;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresMatchAllQuery implements JresQuery {

    private final Map<Object, Object> value = Collections.emptyMap();

    @Override
    public String queryType() {
        return "match_all";
    }

    @JsonValue
    public Map<Object, Object> getValue() {
        return value;
    }
}
