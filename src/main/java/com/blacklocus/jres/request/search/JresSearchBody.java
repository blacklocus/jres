package com.blacklocus.jres.request.search;

import com.blacklocus.jres.request.search.query.JresQuery;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresSearchBody {

    /** Single entry from {@link JresQuery#queryType()} to the JresQuery itself. */
    private Map<String, JresQuery> query;
    private Integer size;

    public JresSearchBody query(JresQuery query) {
        this.query = ImmutableMap.of(query.queryType(), query);
        return this;
    }

    public JresSearchBody size(Integer size) {
        this.size = size;
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serializable getter properties

    public Map<String, JresQuery> getQuery() {
        return query;
    }

    public Integer getSize() {
        return size;
    }

}
