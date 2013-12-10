package com.blacklocus.jres.handler;

import com.blacklocus.jres.response.JresJsonReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.blacklocus.jres.strings.ObjectMappers;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;

import java.io.IOException;

/**
 * Note that {@link #handleResponse(HttpResponse)} of this type may also throw {@link JresErrorReplyException}
 *
 * @param <RESPONSE> response type
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresJsonResponseHandler<RESPONSE extends JresJsonReply> extends AbstractJresResponseHandler<RESPONSE> {

    public JresJsonResponseHandler(Class<RESPONSE> responseClass) {
        super(responseClass);
    }

    @Override
    public RESPONSE handleResponse(HttpResponse http) throws IOException, JresErrorReplyException {

        int statusCode = http.getStatusLine().getStatusCode();

        if (statusCode / 100 == 2) {
            return read(http, getResponseClass());

        } else if (statusCode / 100 == 4) {
            throw read(http, JresErrorReplyException.class);

        } else {
            // Does ElasticSearch ever return 1xx or 3xx (or other)?
            throw new RuntimeException("Erm... dernt nerr wert erm derern?"); // Um, don't know what I'm doing?
        }

    }

    <RESPONSE> RESPONSE read(HttpResponse http, Class<RESPONSE> responseClass) {
        if (http.getEntity() == null) {
            return null;

        } else {
            ContentType contentType = ContentType.parse(http.getEntity().getContentType().getValue());
            if (ContentType.APPLICATION_JSON.getMimeType().equals(contentType.getMimeType())) {
                try {
                    return ObjectMappers.NORMAL.readValue(http.getEntity().getContent(), responseClass);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                throw new RuntimeException("Unable to read content with " + contentType +
                        ". This ResponseHandler can only decode " + ContentType.APPLICATION_JSON);
            }
        }
    }
}
