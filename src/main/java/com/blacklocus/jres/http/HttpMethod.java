package com.blacklocus.jres.http;

import org.apache.http.client.methods.HttpUriRequest;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public interface HttpMethod {

    HttpUriRequest newMethod(String url);

}
