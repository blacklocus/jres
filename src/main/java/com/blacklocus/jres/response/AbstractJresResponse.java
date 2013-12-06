package com.blacklocus.jres.response;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public abstract class AbstractJresResponse<BASIS> implements JresResponse<BASIS> {

    private final BASIS basis;

    public AbstractJresResponse(BASIS basis) {
        this.basis = basis;
    }

    @Override
    public BASIS basis() {
        return basis;
    }

}
