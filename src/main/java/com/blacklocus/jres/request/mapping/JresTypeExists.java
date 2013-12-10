package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.handler.JresPredicates;
import com.blacklocus.jres.request.JresBooleanRequest;
import com.blacklocus.jres.strings.JresPaths;
import com.google.common.base.Predicate;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpHead;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresTypeExists extends JresBooleanRequest {

    private final String index;
    private final String type;

    public JresTypeExists(String index, String type) {
        this.index = index;
        this.type = type;
    }

    @Override
    public String getHttpMethod() {
        return HttpHead.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(index) + type;
    }

    @Override
    public Object getPayload() {
        return null;
    }

    @Override
    public Predicate<HttpResponse> getPredicate() {
        return JresPredicates.STATUS_200;
    }
}
