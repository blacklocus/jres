package com.blacklocus.jres.handler;

import com.google.common.base.Predicate;
import org.apache.http.HttpResponse;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresPredicates {

    public static final Predicate<HttpResponse> STATUS_200 = new Predicate<HttpResponse>() {
        @Override
        public boolean apply(HttpResponse input) {
            return input.getStatusLine().getStatusCode() == 200;
        }
    };
}
