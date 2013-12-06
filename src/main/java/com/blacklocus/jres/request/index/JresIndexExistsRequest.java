package com.blacklocus.jres.request.index;

import com.blacklocus.jres.handler.AbstractPredicateResponseHandler;
import com.blacklocus.jres.handler.JresPredicates;
import com.blacklocus.jres.handler.JresResponseHandler;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.JresBooleanResponse;
import org.apache.http.client.methods.HttpHead;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-exists.html#indices-exists">Index Exists API</a>
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresIndexExistsRequest implements JresRequest<Boolean, JresBooleanResponse> {

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
    public Object getPayload() {
        return null;
    }

    @Override
    public JresResponseHandler<Boolean, JresBooleanResponse> getResponseHandler() {
        return new AbstractPredicateResponseHandler<JresBooleanResponse>(JresPredicates.STATUS_200) {
            @Override
            public JresBooleanResponse makeResponse(Boolean value) {
                return new JresBooleanResponse(value);
            }
        };
    }
}
