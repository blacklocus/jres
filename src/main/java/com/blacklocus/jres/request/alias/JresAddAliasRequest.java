package com.blacklocus.jres.request.alias;

import com.blacklocus.jres.handler.AbstractJsonNodeJresResponseHandler;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.common.JresAcknowledgedResponse;
import com.blacklocus.jres.response.common.JresErrorResponseException;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.codehaus.jackson.JsonNode;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-aliases.html#alias-adding">Add Single Index Alias API</a>
 * <p>
 * Can throw {@link JresErrorResponseException}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresAddAliasRequest implements JresRequest<JsonNode, JresAcknowledgedResponse> {

    private final String index;
    private final String alias;

    /**
     * @param index the actual index
     * @param alias another name by which the actual index may be addressed
     */
    public JresAddAliasRequest(String index, String alias) {
        this.index = index;
        this.alias = alias;
    }

    @Override
    public String getHttpMethod() {
        return HttpPut.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(index) + "_alias/" + alias;
    }

    @Override
    public Object getPayload() {
        return null;
    }

    @Override
    public ResponseHandler<JresAcknowledgedResponse> getResponseHandler() {
        return new AbstractJsonNodeJresResponseHandler<JresAcknowledgedResponse>() {
            @Override
            public JresAcknowledgedResponse makeResponse(JsonNode value) {
                return new JresAcknowledgedResponse(value);
            }
        };
    }
}
