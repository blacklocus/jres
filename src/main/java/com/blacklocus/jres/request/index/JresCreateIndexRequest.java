package com.blacklocus.jres.request.index;

import com.blacklocus.jres.handler.AbstractJsonNodeJresResponseHandler;
import com.blacklocus.jres.handler.JresResponseHandler;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.common.JresAcknowledgedResponse;
import com.blacklocus.jres.response.common.JresErrorResponseException;
import org.apache.http.client.methods.HttpPut;
import org.codehaus.jackson.JsonNode;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-create-index.html#indices-create-index">Create Index API</a>
 * <p>
 * Can throw {@link JresErrorResponseException}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresCreateIndexRequest implements JresRequest<JsonNode, JresAcknowledgedResponse> {

    private final String index;
    private final Object settings;

    public JresCreateIndexRequest(String index) {
        this(index, null);
    }

    public JresCreateIndexRequest(String index, String settingsJson) {
        this.index = index;
        this.settings = settingsJson;
    }

    @Override
    public String getHttpMethod() {
        return HttpPut.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return index;
    }

    @Override
    public Object getPayload() {
        return settings;
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
