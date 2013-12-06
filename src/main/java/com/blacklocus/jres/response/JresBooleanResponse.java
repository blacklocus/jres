package com.blacklocus.jres.response;

import com.blacklocus.jres.request.JresRequest;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresBooleanResponse extends AbstractJresResponse<Boolean> {

    public JresBooleanResponse(JresRequest<Boolean, ?> request, Boolean aBoolean) {
        super(request, aBoolean);
    }

}
