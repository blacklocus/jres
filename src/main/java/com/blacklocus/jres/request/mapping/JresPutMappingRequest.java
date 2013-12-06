package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.handler.AbstractJsonNodeJresResponseHandler;
import com.blacklocus.jres.handler.JresResponseHandler;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.common.JresAcknowledgedResponse;
import com.blacklocus.jres.response.common.JresErrorResponseException;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpPut;
import org.codehaus.jackson.JsonNode;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-put-mapping.html#indices-put-mapping">Put Mapping API</a>
 * <p>
 * Can throw {@link JresErrorResponseException}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresPutMappingRequest implements JresRequest<JsonNode, JresAcknowledgedResponse> {

    private final String index;
    private final String type;
    private final String mappingJson;

    public JresPutMappingRequest(String index, String type) {
        this(index, type, String.format("{\"%s\":{}}", type));
    }

    public JresPutMappingRequest(String index, String type, String mappingJson) {
        this.index = index;
        this.type = type;
        this.mappingJson = mappingJson;
    }

    @Override
    public String getHttpMethod() {
        return HttpPut.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(index) + JresPaths.slashed(type) + "_mapping";
    }

    @Override
    public Object getPayload() {
        return mappingJson;
    }

    @Override
    public JresResponseHandler<JsonNode, JresAcknowledgedResponse> getResponseHandler() {
        return new AbstractJsonNodeJresResponseHandler<JresAcknowledgedResponse>() {
            @Override
            public JresAcknowledgedResponse makeResponse(JsonNode value) {
                return new JresAcknowledgedResponse(value);
            }
        };
    }
}
