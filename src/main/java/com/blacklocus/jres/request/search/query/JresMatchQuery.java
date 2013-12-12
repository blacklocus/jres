package com.blacklocus.jres.request.search.query;

import java.util.HashMap;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresMatchQuery extends HashMap<String, Object> implements JresQuery {

    public JresMatchQuery() {
    }

    public JresMatchQuery(String field, Object subquery) {
        put(field, subquery);
    }

    public JresMatchQuery addField(String field, Object subquery) {
        put(field, subquery);
        return this;
    }

    @Override
    public String queryType() {
        return "match";
    }

}
