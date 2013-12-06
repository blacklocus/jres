package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.handler.AbstractJsonNodeJresResponseHandler;
import com.blacklocus.jres.handler.JresResponseHandler;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.JresJsonNodeResponse;
import com.blacklocus.jres.response.common.JresErrorResponseException;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpGet;
import org.codehaus.jackson.JsonNode;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-get-mapping.html#indices-get-mapping">Get Mapping API</a>
 * <p>
 * Can throw {@link JresErrorResponseException}
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresGetMappingRequest implements JresRequest<JsonNode, JresJsonNodeResponse> {

    private final String index;
    private final String type;

    public JresGetMappingRequest(String index, String type) {
        this.index = index;
        this.type = type;
    }

    @Override
    public String getHttpMethod() {
        return HttpGet.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(index) + JresPaths.slashed(type) + "_mapping";
    }

    @Override
    public Object getPayload() {
        return null;
    }

    @Override
    public JresResponseHandler<JsonNode, JresJsonNodeResponse> getResponseHandler() {
        return new AbstractJsonNodeJresResponseHandler<JresJsonNodeResponse>() {
            @Override
            public JresJsonNodeResponse makeResponse(JsonNode value) {
                return new JresJsonNodeResponse(value);
            }
        };
    }
}
