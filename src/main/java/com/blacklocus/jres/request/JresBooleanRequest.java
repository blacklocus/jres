package com.blacklocus.jres.request;

import com.blacklocus.jres.response.JresBooleanReply;
import com.google.common.base.Predicate;
import org.apache.http.HttpResponse;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public abstract class JresBooleanRequest extends JresJsonRequest<JresBooleanReply> {

    protected JresBooleanRequest() {
        super(JresBooleanReply.class);
    }

    public abstract Predicate<HttpResponse> getPredicate();
}
