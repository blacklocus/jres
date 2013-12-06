package com.blacklocus.jres.handler;

import com.blacklocus.jres.handler.JresResponseHandler;
import com.blacklocus.jres.response.JresResponse;
import com.google.common.base.Predicate;
import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * Returns a boolean value based on a given {@link Predicate} applied directly to the {@link HttpResponse}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public abstract class AbstractPredicateResponseHandler<R extends JresResponse<Boolean>> implements JresResponseHandler<Boolean, R> {

    private final Predicate<HttpResponse> predicate;

    public AbstractPredicateResponseHandler(Predicate<HttpResponse> predicate) {
        this.predicate = predicate;
    }

    @Override
    public R handleResponse(HttpResponse response) throws IOException {
        return makeResponse(predicate.apply(response));
    }

}
