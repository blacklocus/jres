package com.blacklocus.jres.response;

/**
 * @param <BASIS> basic type of response content or answer
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public interface JresResponse<BASIS> {

    /**
     * @return the underlying basis upon which subclass accessors provide additional semantic information
     */
    BASIS basis();

}
