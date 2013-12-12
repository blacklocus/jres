package com.blacklocus.jres.request.search.query;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresDisMaxQuery implements JresQuery {

    /** Each map is a single entry from {@link JresQuery#queryType()} to the JresQuery itself */
    private final List<Map<String, JresQuery>> queries;

    public JresDisMaxQuery(JresQuery... queries) {
        this(Arrays.asList(queries));
    }

    public JresDisMaxQuery(List<JresQuery> queries) {
        this.queries = Lists.transform(queries, new Function<JresQuery, Map<String, JresQuery>>() {
            @Override
            public Map<String, JresQuery> apply(JresQuery input) {
                return ImmutableMap.of(input.queryType(), input);
            }
        });
    }

    @Override
    public String queryType() {
        return "dis_max";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serializable getter properties

    public List<Map<String, JresQuery>> getQueries() {
        return queries;
    }
}
