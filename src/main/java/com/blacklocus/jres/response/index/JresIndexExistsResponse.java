package com.blacklocus.jres.response.index;

import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.AbstractJresResponse;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresIndexExistsResponse extends AbstractJresResponse<Boolean> {

    public JresIndexExistsResponse(JresRequest<Boolean, ?> request, Boolean verity) {
        super(request, verity);
    }

    public boolean indexExists() {
        return basis();
    }
}
