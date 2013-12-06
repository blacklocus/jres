package com.blacklocus.jres;

import com.blacklocus.jres.http.HttpClientFactory;
import com.blacklocus.jres.http.HttpMethods;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.JresResponse;
import com.blacklocus.jres.strings.JresPaths;
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

    /**
     * @param hostOrHosts externalized host name provider to support arbitrary request load allocation
     */
    public Jres(Supplier<String> hostOrHosts) {
        this.hosts = hostOrHosts;
        this.http = HttpClientFactory.create(30 * 1000, 300 * 1000); // 30 sec & 5 min
    }

    /**
     *
     * @param <Q> request - type of request object
     * @param <R> response - type of response object produced as the returned value
     * @param <B> basis - basic type of value encapsulated by the response. Think of it as a shallow interpretation
     *           directly of the ElasticSear
     * @return corresponding response object ({@link JresRequest}'s RESPONSE type)
     */
    public <B, R extends JresResponse<B>, Q extends JresRequest<B, R>> R request(Q request) {

        String url = JresPaths.slashed(hosts.get()) + request.getPath();
        try {
            HttpUriRequest httpRequest = HttpMethods.createRequest(request.getHttpMethod(), url, request.getPayload());
            // We like the one that takes a ResponseHandler because supposedly that should prevent http resource
            // leaks whether botched local code or unexpected exceptions.
            return http.execute(httpRequest, request.getResponseHandler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

