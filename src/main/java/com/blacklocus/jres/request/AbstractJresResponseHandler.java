package com.blacklocus.jres.request;

import com.blacklocus.jres.response.JresResponse;
import com.blacklocus.jres.str.ObjectMappers;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * @param <R> response type
 * @author Jason Dunkelberger (dirkraft)
 */
public abstract class AbstractJresResponseHandler<R extends JresResponse> implements ResponseHandler<R> {

    private final ObjectMapper objectMapper;
    private final JresRequest<R> request;

    public AbstractJresResponseHandler(JresRequest<R> request) {
        this.objectMapper = ObjectMappers.NORMAL;
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
