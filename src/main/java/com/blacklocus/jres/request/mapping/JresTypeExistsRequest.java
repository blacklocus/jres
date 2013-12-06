package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.handler.AbstractPredicateResponseHandler;
import com.blacklocus.jres.handler.JresPredicates;
import com.blacklocus.jres.handler.JresResponseHandler;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.JresBooleanResponse;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpHead;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresTypeExistsRequest implements JresRequest<Boolean, JresBooleanResponse> {

    private final String index;
    private final String type;

    public JresTypeExistsRequest(String index, String type) {
        this.index = index;
        this.type = type;
    }

    @Override
    public String getHttpMethod() {
        return HttpHead.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(index) + type;
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
