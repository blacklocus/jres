package com.blacklocus.jres.response;

import com.blacklocus.jres.request.JresRequest;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public abstract class AbstractJresResponse<BASIS> implements JresResponse<BASIS> {

    private final JresRequest<BASIS, ?> request;
    private final BASIS basis;

    protected AbstractJresResponse(JresRequest<BASIS, ?> request, BASIS basis) {
        this.request = request;
        this.basis = basis;
    }

    @Override
    public JresRequest<BASIS, ?> getRequest() {
        return request;
    }

    @Override
    public BASIS basis() {
        return basis;
    }

}
