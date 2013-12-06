package com.blacklocus.jres.request.index;

import com.blacklocus.jres.request.AbstractPredicateResponseHandler;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.index.JresIndexExistsResponse;
import com.google.common.base.Predicate;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpHead;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-exists.html#indices-exists">Index Exists API</a>
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresIndexExistsRequest implements JresRequest<Boolean, JresIndexExistsResponse> {

    private final String index;

    public JresIndexExistsRequest(String index) {
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
    public ResponseHandler<JresIndexExistsResponse> getResponseHandler() {
        return new AbstractPredicateResponseHandler<JresIndexExistsResponse>(new Predicate<HttpResponse>() {
            @Override
            public boolean apply(HttpResponse input) {
                return input.getStatusLine().getStatusCode() == 200;
            }
        }) {
            @Override
            public JresIndexExistsResponse makeResponse(Boolean value) {
                return new JresIndexExistsResponse(JresIndexExistsRequest.this, value);
            }
        };
    }
}
