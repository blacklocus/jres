package com.blacklocus.jres.handler;

import com.blacklocus.jres.response.JresReply;
import org.apache.http.client.ResponseHandler;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
abstract class AbstractJresResponseHandler<RESPONSE extends JresReply> implements ResponseHandler<RESPONSE> {

    private final Class<RESPONSE> responseClass;

    AbstractJresResponseHandler(Class<RESPONSE> responseClass) {
        this.responseClass = responseClass;
    }

    public Class<RESPONSE> getResponseClass() {
        return responseClass;
    }

}
