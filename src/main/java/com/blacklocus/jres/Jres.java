package com.blacklocus.jres;

import com.blacklocus.jres.http.HttpClientFactory;
import com.blacklocus.jres.http.HttpMethods;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.JresResponse;
import com.blacklocus.jres.response.common.JresErrorResponseException;
import com.blacklocus.jres.strings.JresPaths;
import com.google.common.base.Supplier;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.codehaus.jackson.JsonNode;

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
     * @param <Q> request - type of request object
     * @param <R> response - type of response object produced as the returned value
     * @param <B> basis - basic type of value encapsulated by the response. Think of it as a shallow interpretation
     *            directly of the ElasticSearch response body.
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

    /**
     * Like {@link #request(JresRequest)} but wraps in a try-catch for {@link JresErrorResponseException} and returns
     * it gracefully if the status code matches that given. This is sometimes preferable to inline try-catch blocks for
     * expected error responses, but otherwise functionally equivalent -- e.g. checking if an index exists will return
     * a 404 which is normally translated into a thrown JresErrorResponseException. If 404 was given as the
     * tolerated status then such a JresErrorResponseException will be wrapped up into a {@link Tolerance}.
     * <p/>
     * Currently assumes a JsonNode basis for now, to keep exposed signatures' generics simpler.
     *
     * @param request         JresRequest with JsonNode basis
     * @param toleratedStatus which if encountered will return wrapped up in a Tolerance instead of being thrown
     * @param <Q>             request - type of request object
     * @param <R>             response - type of response object produced as the returned value
     * @return a Tolerance which wraps up the possible ok response or error response. Check {@link Tolerance#isError()}.
     * @see Tolerance
     */
    public <R extends JresResponse<JsonNode>, Q extends JresRequest<JsonNode, R>> Tolerance<R> tolerate(Q request, int toleratedStatus) {

        try {
            return new Tolerance<R>(false, request(request));
        } catch (JresErrorResponseException e) {
            if (e.getStatus() != toleratedStatus) {
                throw e;
            } else {
                return new Tolerance<R>(true, e);
            }
        }

    }

    /**
     * @param <R> response - type of response object produced for a successful ElasticSearch response. Currently assumes
     *            a JsonNode basis.
     */
    public static class Tolerance<R extends JresResponse<JsonNode>> {
        // Assume JsonNode basis for now, keep exposed signatures' generics simpler.

        private final boolean error;
        private final JresErrorResponseException exception;
        private final R response;

        Tolerance(boolean error, R response) {
            this.error = error;
            this.response = response;
            this.exception = null;
        }

        Tolerance(boolean error, JresErrorResponseException exception) {
            this.error = error;
            this.exception = exception;
            this.response = null;
        }

        /**
         * @return whether or not an error response was captured: <code>true</code> {@link #getError()} is not null,
         * <code>false</code> {@link #getResponse()} is not null
         */
        public boolean isError() {
            return error;
        }

        /**
         * @return captured {@link JresErrorResponseException}, if the request resulted in an error response
         */
        public JresErrorResponseException getError() {
            return exception;
        }

        /**
         * @return captured {@link JresResponse}, if the request was ok
         */
        public R getResponse() {
            return response;
        }

    }
}

