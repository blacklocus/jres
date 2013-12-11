package com.blacklocus.jres.handler;

import com.blacklocus.jres.response.JresReply;
import org.apache.http.client.ResponseHandler;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
abstract class AbstractJresResponseHandler<REPLY extends JresReply> implements ResponseHandler<REPLY> {

    private final Class<REPLY> replyClass;

    AbstractJresResponseHandler(Class<REPLY> replyClass) {
        this.replyClass = replyClass;
    }

    public Class<REPLY> getReplyClass() {
        return replyClass;
    }

}
