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

import com.blacklocus.jres.response.JresJsonReply;

import java.io.IOException;
import java.net.URL;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresRawRequest<REPLY extends JresJsonReply> extends JresJsonRequest<REPLY> {

    private final String method;
    private final String path;
    private final Object body;

    @SuppressWarnings("unchecked")
    public JresRawRequest(String method, String path) {
        this(method, path, (Object) null, (Class<REPLY>) JresJsonReply.class);
    }

    @SuppressWarnings("unchecked")
    public JresRawRequest(String method, String path, Object body) {
        this(method, path, body, (Class<REPLY>) JresJsonReply.class);
    }

    public JresRawRequest(String method, String path, Class<REPLY> replyClass) {
        this(method, path, (Object) null, replyClass);
    }

    public JresRawRequest(String method, String path, URL body, Class<REPLY> replyClass) {
        this(method, path, url(body), replyClass);
    }

    public JresRawRequest(String method, String path, Object body, Class<REPLY> replyClass) {
        super(replyClass);
        this.method = method;
        this.path = path;
        this.body = body;
    }

    @Override
    public String getHttpMethod() {
        return method;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Object getPayload() {
        return body;
    }

    private static Object url(URL url) {
        try {
            return url.getContent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
