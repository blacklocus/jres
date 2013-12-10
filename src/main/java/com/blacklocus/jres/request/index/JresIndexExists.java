package com.blacklocus.jres.request.index;

import com.blacklocus.jres.handler.JresPredicates;
import com.blacklocus.jres.request.JresBooleanRequest;
import com.google.common.base.Predicate;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpHead;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-exists.html#indices-exists">Index Exists API</a>
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresIndexExists extends JresBooleanRequest {

    private final String index;

    public JresIndexExists(String index) {
        this.index = index;
    }

    @Override
    public String getHttpMethod() {
        return HttpHead.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return index;
    }

    @Override
    public Object getPayload() {
        return null;
    }

    @Override
    public Predicate<HttpResponse> getPredicate() {
        return JresPredicates.STATUS_200;
    }
}
