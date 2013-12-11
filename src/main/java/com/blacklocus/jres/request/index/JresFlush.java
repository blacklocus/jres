package com.blacklocus.jres.request.index;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.common.JresShardsReply;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpPost;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresFlush extends JresJsonRequest<JresShardsReply> {

    private final String index;

    public JresFlush(String index) {
        super(JresShardsReply.class);
        this.index = index;
    }

    @Override
    public String getHttpMethod() {
        return HttpPost.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(index) + "_flush";
    }

    @Override
    public Object getPayload() {
        return null;
    }
}
