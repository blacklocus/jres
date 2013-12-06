package com.blacklocus.jres.response;

import com.blacklocus.jres.request.JresRequest;

/**
 * @param <BASIS> basic type of response content or answer
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public interface JresResponse<BASIS> {

    /**
     * @return the request that resulted in this response
     */
    JresRequest<BASIS, ?> getRequest();

    /**
     * @return the underlying basis upon which subclass accessors provide additional semantic information
     */
    BASIS basis();

}
