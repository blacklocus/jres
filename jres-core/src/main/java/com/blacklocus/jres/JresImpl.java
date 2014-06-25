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
package com.blacklocus.jres;

import com.blacklocus.jres.handler.JresJsonResponseHandler;
import com.blacklocus.jres.handler.JresPredicatedResponseHandler;
import com.blacklocus.jres.http.HttpClientFactory;
import com.blacklocus.jres.http.HttpMethods;
import com.blacklocus.jres.request.JresBooleanRequest;
import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.response.JresBooleanReply;
import com.blacklocus.jres.response.JresJsonReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.blacklocus.jres.strings.JresPaths;
import com.blacklocus.jres.strings.ObjectMappers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Iterators;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class JresImpl implements Jres {

    private final Supplier<String> hosts;
    private final HttpClient http;

    public JresImpl(String elasticSearchHost) {
        this(Suppliers.ofInstance(elasticSearchHost));

    }

    public JresImpl(final List<String> elasticSearchHostOrHosts) {
        this(new Supplier<String>() {
            final Iterator<String> cycler = Iterators.cycle(elasticSearchHostOrHosts);

            @Override
            public String get() {
                return cycler.next();
            }
        });
    }

    /**
     * @param elasticSearchHostOrHosts externalized host name provider to support arbitrary request load allocation. Use
     *                    <code>{@link Suppliers#ofInstance(Object) Suppliers.ofInstance(host)}</code> for single entry.
     */
    public JresImpl(Supplier<String> elasticSearchHostOrHosts) {
        this.hosts = elasticSearchHostOrHosts;
        this.http = HttpClientFactory.create(30 * 1000, 300 * 1000); // 30 sec & 5 min
    }

    /**
     * There are a few requests that return no JSON and are based entirely on status codes.
     */
    @Override
    public <REQUEST extends JresBooleanRequest> JresBooleanReply bool(REQUEST request) {

        String url = JresPaths.slashed(hosts.get()) + request.getPath();
        try {
            HttpUriRequest httpRequest = HttpMethods.createRequest(request.getHttpMethod(), url, request.getPayload());
            // We like the one that takes a ResponseHandler because supposedly that should prevent http resource
            // leaks whether botched local code or unexpected exceptions.
            return http.execute(httpRequest, new JresPredicatedResponseHandler(request.getPredicate()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @param <QUEST>  request - type of request object
     * @param <REPLY> response - type of response object produced as the returned value
     * @return corresponding response object ({@link JresRequest}'s RESPONSE type)
     */
    @Override
    public <REPLY extends JresJsonReply, QUEST extends JresJsonRequest<REPLY>> REPLY quest(QUEST quest) {

        String url = JresPaths.slashed(hosts.get()) + quest.getPath();
        try {
            HttpUriRequest httpRequest = HttpMethods.createRequest(quest.getHttpMethod(), url, quest.getPayload());
            // We like the one that takes a ResponseHandler because supposedly that should prevent http resource
            // leaks whether botched local code or unexpected exceptions.
            return http.execute(httpRequest, new JresJsonResponseHandler<REPLY>(quest.getResponseClass()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Like {@link #quest(JresJsonRequest)} but wraps in a try-catch for {@link JresErrorReplyException} and returns
     * it gracefully if the status code matches that given. This is sometimes preferable to inline try-catch blocks for
     * expected error responses, but otherwise functionally equivalent -- e.g. checking if an index exists will return
     * a 404 which is normally translated into a thrown JresErrorResponseException. If 404 was given as the
     * tolerated status then such a JresErrorResponseException will be wrapped up into a {@link Jres.Tolerance}.
     * <p/>
     * Currently assumes a JsonNode basis for now, to keep exposed signatures' generics simpler.
     *
     * @param quest         JresRequest with JsonNode basis
     * @param toleratedStatus which if encountered will return wrapped up in a Tolerance instead of being thrown
     * @param <QUEST>       request - type of request object
     * @param <REPLY>      response - type of response object produced as the returned value
     * @return a Tolerance which wraps up the possible ok response or error response. Check {@link Jres.Tolerance#isError()}.
     * @see Jres.Tolerance
     */
    @Override
    public <REPLY extends JresJsonReply, QUEST extends JresJsonRequest<REPLY>> Tolerance<REPLY> tolerate(QUEST quest, int toleratedStatus) {

        try {
            return new Tolerance<REPLY>(false, quest(quest));
        } catch (JresErrorReplyException e) {
            if (e.getStatus() != toleratedStatus) {
                throw e;
            } else {
                return new Tolerance<REPLY>(true, e);
            }
        }

    }

    public static <T> T load(URL script, Class<T> klass) {
        try {
            // Don't use ObjectMapper.readValue(URL, ?), doesn't seem to be able to find local resources.
            return ObjectMappers.fromJson(script.openStream(), klass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T load(URL script, TypeReference<T> typeReference) {
        try {
            // Don't use ObjectMapper.readValue(URL, ?), doesn't seem to be able to find local resources.
            return ObjectMappers.fromJson(script.openStream(), typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

