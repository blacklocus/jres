package com.blacklocus.jres.request;

import com.blacklocus.jres.response.JresReply;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public abstract class JresJsonRequest<RESPONSE extends JresReply> implements JresRequest<RESPONSE> {

    private final Class<RESPONSE> responseClass;

    protected JresJsonRequest(Class<RESPONSE> responseClass) {
        this.responseClass = responseClass;
    }

    @Override
    public final Class<RESPONSE> getResponseClass() {
        return responseClass;
    }
}
