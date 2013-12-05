package com.blacklocus.jres.response.handler;

import com.blacklocus.jres.request.JresRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * @param <R> response type
 * @author Jason Dunkelberger (dirkraft)
 */
public abstract class AbstractJresResponseHandler<R> implements JresResponseHandler<R> {

    private final ObjectMapper objectMapper;
    private final JresRequest<R> request;

    public AbstractJresResponseHandler(JresRequest<R> request) {
        this.objectMapper = new ObjectMapper();
        this.request = request;
    }

    @Override
    public R handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        JsonNode node = null;
        if (response.getEntity() != null) {
            node = objectMapper.readValue(response.getEntity().getContent(), JsonNode.class);
        }
        return makeResponse(request, node);
    }

    public abstract R makeResponse(JresRequest<R> request, JsonNode node);
}
