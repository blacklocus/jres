package com.blacklocus.jres;

import com.blacklocus.jres.http.HttpClientFactory;
import com.blacklocus.jres.http.HttpMethods;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.JresResponse;
import com.blacklocus.jres.str.JresPaths;
import com.google.common.base.Supplier;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class Jres {

    private final Supplier<String> hosts;
    private final HttpClient http;

    public Jres(Supplier<String> hostOrHosts) {
        this.hosts = hostOrHosts;
        this.http = HttpClientFactory.create(30, 300);
    }

    public <R extends JresResponse, Q extends JresRequest<R>> R request(Q request) {

        String url = JresPaths.slashed(hosts.get()) + request.getPath();
        try {
            HttpUriRequest httpRequest = HttpMethods.createRequest(request.getHttpMethod(), url);
            // We like the one that takes a ResponseHandler because supposedly that should prevent http resource
            // leaks whether botched local code or unexpected exceptions.
            return http.execute(httpRequest, request.getResponseHandler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

