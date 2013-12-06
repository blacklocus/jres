package com.blacklocus.jres.handler;

import org.apache.http.client.ResponseHandler;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public interface JresResponseHandler<B, R> extends ResponseHandler<R> {

    public abstract R makeResponse(B value);

}
