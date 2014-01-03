/**
 * Copyright 2013 BlackLocus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blacklocus.jres.request;

import com.blacklocus.jres.response.JresReply;

/**
 * @param <REPLY> response type - the wrapper object built by Jres to encapsulte the full response
 * @author Jason Dunkelberger (dirkraft)
 */
public interface JresRequest<REPLY extends JresReply> {

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
    Class<REPLY> getResponseClass();

}
