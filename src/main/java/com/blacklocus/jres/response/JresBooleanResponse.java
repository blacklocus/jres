package com.blacklocus.jres.response;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresBooleanResponse extends AbstractJresResponse<Boolean> {

    public JresBooleanResponse(Boolean aBoolean) {
        super(aBoolean);
    }

    /**
     * alias to {@link #basis()}, sometimes more descriptive in code
     */
    public Boolean verity() {
        return basis();
    }

}
