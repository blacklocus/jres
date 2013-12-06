package com.blacklocus.jres.request;

import com.blacklocus.jres.response.JresResponse;
import com.blacklocus.jres.strings.ObjectMappers;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * @param <R> response type
 * @author Jason Dunkelberger (dirkraft)
 */
public abstract class AbstractJsonNodeJresResponseHandler<R extends JresResponse<JsonNode>> implements JresResponseHandler<JsonNode, R> {

    private final ObjectMapper objectMapper;

    public AbstractJsonNodeJresResponseHandler() {
        this.objectMapper = ObjectMappers.NORMAL;
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
        return makeResponse(node);
    }

}
