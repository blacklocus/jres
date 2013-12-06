package com.blacklocus.jres.handler;

import com.blacklocus.jres.handler.JresResponseHandler;
import com.blacklocus.jres.response.JresResponse;
import com.blacklocus.jres.response.common.JresErrorResponseException;
import com.blacklocus.jres.strings.ObjectMappers;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Note that {@link #handleResponse(HttpResponse)} of this type may also throw {@link JresErrorResponseException}
 *
 * @param <R> response type
 * @author Jason Dunkelberger (dirkraft)
 */
public abstract class AbstractJsonNodeJresResponseHandler<R extends JresResponse<JsonNode>> implements JresResponseHandler<JsonNode, R> {

    private final ObjectMapper objectMapper;

    public AbstractJsonNodeJresResponseHandler() {
        this.objectMapper = ObjectMappers.NORMAL;
    }

    @Override
    public R handleResponse(HttpResponse response) throws IOException, JresErrorResponseException {
        JsonNode node = null;

        int statusCode = response.getStatusLine().getStatusCode();

        if (response.getEntity() != null) {
            ContentType contentType = ContentType.parse(response.getEntity().getContentType().getValue());
            if (ContentType.APPLICATION_JSON.getMimeType().equals(contentType.getMimeType())) {
                node = objectMapper.readValue(response.getEntity().getContent(), JsonNode.class);

            } else {
                throw new RuntimeException("Unable to read content with " + contentType +
                        ". This ResponseHandler can only decode " + ContentType.APPLICATION_JSON);
            }
        }

        if (statusCode / 100 == 2) {
            return makeResponse(node);

        } else if (statusCode / 100 == 4) {
            throw new JresErrorResponseException(node);

        } else {
            // Does ElasticSearch ever return 1xx or 3xx (or other)?
            throw new RuntimeException("Erm... dernt nerr wert erm derern?"); // Um, don't know what I'm doing?
        }

    }

}
