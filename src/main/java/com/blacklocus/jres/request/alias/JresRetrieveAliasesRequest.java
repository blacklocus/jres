package com.blacklocus.jres.request.alias;

import com.blacklocus.jres.handler.AbstractJsonNodeJresResponseHandler;
import com.blacklocus.jres.handler.JresResponseHandler;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.alias.JresRetrieveAliasesResponse;
import com.blacklocus.jres.response.common.JresErrorResponseException;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpGet;
import org.codehaus.jackson.JsonNode;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-aliases.html#alias-adding">Add Single Index Alias API</a>
 * <p>
 * Can throw {@link JresErrorResponseException}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresRetrieveAliasesRequest implements JresRequest<JsonNode, JresRetrieveAliasesResponse> {

    private final String indexPattern;
    private final String aliasPattern;

    /**
     * @param indexPattern which appears to accept any number of '*' wildcards interspersed with characters and multiple
     *                     expressions separated by ','
     * @param aliasPattern which appears to accept any number of '*' wildcards interspersed with characters and multiple
     *                     expressions separated by ','
     */
    public JresRetrieveAliasesRequest(String indexPattern, String aliasPattern) {
        this.indexPattern = indexPattern;
        this.aliasPattern = aliasPattern;
    }

    @Override
    public String getHttpMethod() {
        return HttpGet.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(indexPattern) + "_alias/" + aliasPattern;
    }

    @Override
    public Object getPayload() {
        return null;
    }

    @Override
    public JresResponseHandler<JsonNode, JresRetrieveAliasesResponse> getResponseHandler() {
        return new AbstractJsonNodeJresResponseHandler<JresRetrieveAliasesResponse>() {
            @Override
            public JresRetrieveAliasesResponse makeResponse(JsonNode value) {
                return new JresRetrieveAliasesResponse(value);
            }
        };
    }

}
