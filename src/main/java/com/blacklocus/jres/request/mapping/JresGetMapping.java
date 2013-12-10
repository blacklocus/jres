package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.JresJsonReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpGet;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-get-mapping.html#indices-get-mapping">Get Mapping API</a>
 * <p>
 * Can throw {@link JresErrorReplyException}
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresGetMapping extends JresJsonRequest<JresJsonReply> {

    private final String index;
    private final String type;

    public JresGetMapping(String index, String type) {
        super(JresJsonReply.class);
        this.index = index;
        this.type = type;
    }

    @Override
    public String getHttpMethod() {
        return HttpGet.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(index) + JresPaths.slashed(type) + "_mapping";
    }

    @Override
    public Object getPayload() {
        return null;
    }

}
