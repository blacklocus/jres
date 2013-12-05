package com.blacklocus.jres.request;

import com.blacklocus.jres.response.JresResponse;
import com.blacklocus.jres.str.ObjectMappers;
import com.google.common.net.MediaType;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;
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
    public R handleResponse(HttpResponse response) throws IOException {
        JsonNode node = null;
        if (response.getEntity() != null) {

            ContentType contentType = ContentType.parse(response.getEntity().getContentType().getValue());
            if (ContentType.APPLICATION_JSON.getMimeType().equals(contentType.getMimeType())) {
                node = objectMapper.readValue(response.getEntity().getContent(), JsonNode.class);

            } else {
                throw new RuntimeException("Unable to read content with " + contentType +
                        ". This ResponseHandler can only decode " + ContentType.APPLICATION_JSON);
            }
        }
        return makeResponse(request, node);
    }

    public abstract R makeResponse(JresRequest<R> request, JsonNode node);
}
