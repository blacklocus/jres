package com.blacklocus.jres.request;

import com.blacklocus.jres.response.JresReply;

/**
 * @param <RESPONSE> response type - the wrapper object built by Jres to encapsulte the full response
 * @author Jason Dunkelberger (dirkraft)
 */
public interface JresRequest<RESPONSE extends JresReply> {

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
     * @return request body if the request should have one. The object will automatically be serialized to JSON if it
     * is not already (a String). May be <code>null</code>.
     */
    Object getPayload();

    /**
     * @return response object encapsulation type
     */
    Class<RESPONSE> getResponseClass();

}
