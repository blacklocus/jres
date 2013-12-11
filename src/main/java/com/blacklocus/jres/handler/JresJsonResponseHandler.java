package com.blacklocus.jres.handler;

import com.blacklocus.jres.response.JresJsonReply;
import com.blacklocus.jres.response.JresReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.blacklocus.jres.strings.ObjectMappers;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Note that {@link #handleResponse(HttpResponse)} of this type may also throw {@link JresErrorReplyException}
 *
 * @param <REPLY> response type
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresJsonResponseHandler<REPLY extends JresJsonReply> extends AbstractJresResponseHandler<REPLY> {

    private static final Logger LOG = LoggerFactory.getLogger(JresJsonResponseHandler.class);

    public JresJsonResponseHandler(Class<REPLY> replyClass) {
        super(replyClass);
    }

    @Override
    public REPLY handleResponse(HttpResponse http) throws IOException, JresErrorReplyException {

        int statusCode = http.getStatusLine().getStatusCode();

        if (statusCode / 100 == 2) {
            Pair<JsonNode, REPLY> replyPair = read(http, getReplyClass());
            REPLY reply = replyPair.getRight();
            reply.node(replyPair.getLeft());
            return reply;

        } else if (statusCode / 100 == 4) {
            Pair<JsonNode, JresErrorReplyException> replyPair = read(http, JresErrorReplyException.class);
            JresErrorReplyException exception = replyPair.getRight();
            exception.node(replyPair.getLeft());
            throw exception;

        } else {
            // Does ElasticSearch ever return 1xx or 3xx (or other)?
            throw new RuntimeException("Erm... dernt nerr wert erm derern?"); // Um, don't know what I'm doing?
        }

    }

    <RESPONSE extends JresReply> Pair<JsonNode, RESPONSE> read(HttpResponse http, Class<RESPONSE> responseClass) {
        if (http.getEntity() == null) {
            return null;

        } else {
            ContentType contentType = ContentType.parse(http.getEntity().getContentType().getValue());
            if (ContentType.APPLICATION_JSON.getMimeType().equals(contentType.getMimeType())) {
                try {

                    JsonNode node = ObjectMappers.NORMAL.readValue(http.getEntity().getContent(), JsonNode.class);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(ObjectMappers.NORMAL.writeValueAsString(node));
                    }
                    return Pair.of(node, ObjectMappers.NORMAL.readValue(node, responseClass));

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
