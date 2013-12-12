package com.blacklocus.jres.request;

import com.blacklocus.jres.response.JresReply;

import java.io.IOException;
import java.net.URL;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresRawRequest<REPLY extends JresReply> extends JresJsonRequest<REPLY> {

    private final String method;
    private final String path;
    private final Object body;

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
