package com.blacklocus.jres.http;

import com.blacklocus.jres.strings.ObjectMappers;
import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpDelete;
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
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.util.Map;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class HttpMethods {

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
                    return new HttpDelete(url);
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

    /**
     * @param method http method, case-insensitive
     * @param url destination of request
     * @param payload (optional) request body, serialized to JSON (so, a String) if it is not already
     * @return HttpUriRequest with header <code>Accept: application/json; charset=UTF-8</code>
     */
    public static HttpUriRequest createRequest(String method, String url, Object payload) {
        HttpUriRequest httpUriRequest = METHODS.get(method.toUpperCase()).newMethod(url);
        httpUriRequest.addHeader("Accept", ContentType.APPLICATION_JSON.toString());
        if (payload != null) {
            String entity;
            try {
                entity = (payload instanceof String) ? (String) payload : ObjectMappers.NORMAL.writeValueAsString(payload);
                ((HttpEntityEnclosingRequest) httpUriRequest).setEntity(new StringEntity(entity, ContentType.APPLICATION_JSON));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return httpUriRequest;
    }

}
