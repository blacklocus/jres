package com.blacklocus.jres.request;

import org.apache.http.client.ResponseHandler;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public interface JresResponseHandler<B, R> extends ResponseHandler<R> {

    public abstract R makeResponse(B value);

}
