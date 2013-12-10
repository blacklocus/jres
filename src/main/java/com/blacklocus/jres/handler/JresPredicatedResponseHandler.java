package com.blacklocus.jres.handler;

import com.blacklocus.jres.response.JresBooleanReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.google.common.base.Predicate;
import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresPredicatedResponseHandler extends AbstractJresResponseHandler<JresBooleanReply> {

    private final Predicate<HttpResponse> predicate;

    public JresPredicatedResponseHandler(Predicate<HttpResponse> predicate) {
        super(JresBooleanReply.class);
        this.predicate = predicate;
    }

    @Override
    public JresBooleanReply handleResponse(HttpResponse http) throws IOException, JresErrorReplyException {
        return new JresBooleanReply(predicate.apply(http));
    }

}
