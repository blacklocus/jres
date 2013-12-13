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
package com.blacklocus.jres.http;

import com.blacklocus.jres.strings.ObjectMappers;
import com.blacklocus.misc.ExceptingRunnable;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class HttpMethods {

    private static final Logger LOG = LoggerFactory.getLogger(HttpMethods.class);

    private static final Map<String, HttpMethod> METHODS = ImmutableMap.<String, HttpMethod>builder()
            .put(HttpGet.METHOD_NAME, new HttpMethod() {
                @Override
                public HttpRequestBase newMethod(String url) {
                    return new HttpGet(url);
                }
            }).put(HttpPost.METHOD_NAME, new HttpMethod() {
                @Override
                public HttpRequestBase newMethod(String url) {
                    return new HttpPost(url);
                }
            }).put(HttpPut.METHOD_NAME, new HttpMethod() {
                @Override
                public HttpRequestBase newMethod(String url) {
                    return new HttpPut(url);
                }
            }).put(HttpDelete.METHOD_NAME, new HttpMethod() {
                @Override
                public HttpRequestBase newMethod(String url) {
                    HttpEntityEnclosingRequestBase request = new HttpEntityEnclosingRequestBase() {
                        @Override
                        public String getMethod() {
                            return HttpDelete.METHOD_NAME;
                        }
                    };
                    request.setURI(URI.create(url));
                    return request;
                }
            }).put(HttpHead.METHOD_NAME, new HttpMethod() {
                @Override
                public HttpRequestBase newMethod(String url) {
                    return new HttpHead(url);
                }
            }).put(HttpOptions.METHOD_NAME, new HttpMethod() {
                @Override
                public HttpRequestBase newMethod(String url) {
                    return new HttpOptions(url);
                }
            }).put(HttpTrace.METHOD_NAME, new HttpMethod() {
                @Override
                public HttpRequestBase newMethod(String url) {
                    return new HttpTrace(url);
                }
            }).put(HttpPatch.METHOD_NAME, new HttpMethod() {
                @Override
                public HttpRequestBase newMethod(String url) {
                    return new HttpPatch(url);
                }
            }).build();

    private static final ExecutorService PIPER = Executors.newCachedThreadPool();

    /**
     * @param method  http method, case-insensitive
     * @param url     destination of request
     * @param payload (optional) request body. An InputStream or String will be sent as is, while any other type will
     *                be serialized with {@link ObjectMappers#NORMAL} to JSON.
     * @return HttpUriRequest with header <code>Accept: application/json; charset=UTF-8</code>
     */
    public static HttpUriRequest createRequest(String method, String url, final Object payload) {

        HttpUriRequest httpUriRequest = METHODS.get(method.toUpperCase()).newMethod(url);
        httpUriRequest.addHeader("Accept", ContentType.APPLICATION_JSON.toString());

        if (payload != null) {
            try {

                // This cast will except if a body is given for non-HttpEntityEnclosingRequest types. Deal with it later.
                ((HttpEntityEnclosingRequest) httpUriRequest).setEntity(createEntity(payload));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return httpUriRequest;
    }

    static HttpEntity createEntity(final Object payload) throws IOException {
        final HttpEntity entity;
        if (payload instanceof InputStream) {

            if (LOG.isDebugEnabled()) {
                String stream = IOUtils.toString((InputStream) payload);
                LOG.debug(stream);
                entity = new StringEntity(stream, ContentType.APPLICATION_JSON);
            } else {
                entity = new InputStreamEntity((InputStream) payload, ContentType.APPLICATION_JSON);
            }

        } else if (payload instanceof String) {

            LOG.debug((String) payload);
            entity = new StringEntity((String) payload, ContentType.APPLICATION_JSON);

        } else { // anything else will be serialized with Jackson

            if (LOG.isDebugEnabled()) {
                String json = ObjectMappers.NORMAL.writeValueAsString(payload);
                LOG.debug(json);
                entity = new StringEntity(json, ContentType.APPLICATION_JSON);

            } else {
                final PipedOutputStream pipedOutputStream = new PipedOutputStream();
                final PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
                PIPER.submit(new ExceptingRunnable() {
                    @Override
                    protected void go() throws Exception {
                        try {
                            ObjectMappers.NORMAL.writeValue(pipedOutputStream, payload);
                            pipedOutputStream.flush();
                        } finally {
                            IOUtils.closeQuietly(pipedOutputStream);
                        }
                    }
                });
                entity = new InputStreamEntity(pipedInputStream, ContentType.APPLICATION_JSON);
            }

        }
        return entity;
    }
}
