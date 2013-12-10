package com.blacklocus.jres.request.index;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.JresJsonReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpGet;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-get-settings.html#indices-get-settings">Get Index Settings API</a>
 * <p>
 * Can throw {@link JresErrorReplyException}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresGetIndexSettings extends JresJsonRequest<JresJsonReply> {

    private final String index;

    public JresGetIndexSettings(String index) {
        super(JresJsonReply.class);
        this.index = index;
    }

    @Override
    public String getHttpMethod() {
        return HttpGet.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(index) + "_settings";
    }

    @Override
    public Object getPayload() {
        return null;
    }

}
