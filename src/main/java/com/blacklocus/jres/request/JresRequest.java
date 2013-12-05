package com.blacklocus.jres.request;

import org.apache.http.client.ResponseHandler;

/**
 * @param <R> response type
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public interface JresRequest<R> {

    /**
     * @return one of the standard http request methods, case-insensitive: [get, post, put, del, options, trace, head, patch]
     */
    String getHttpMethod();

    /**
     * @return the rest of url after the ElasticSearch host without a leading slash, e.g. <code>myIndex/myType/135</code>
     * or <code>_nodes</code> or <code>myIndex/_settings?pretty</code>
     */
    String getPath();

    /**
     * @return response handler which will parse the returned JsonNode, or <code>null</code> to disregard any response
     * content
     */
    ResponseHandler<R> getResponseHandler();
}
