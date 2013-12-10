package com.blacklocus.jres.request.index;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.common.JresAcknowledgedReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.apache.http.client.methods.HttpPut;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-create-index.html#indices-create-index">Create Index API</a>
 * <p>
 * Can throw {@link JresErrorReplyException}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresCreateIndex extends JresJsonRequest<JresAcknowledgedReply> {

    private final String index;
    private final Object settings;

    public JresCreateIndex(String index) {
        this(index, null);
    }

    public JresCreateIndex(String index, String settingsJson) {
        super(JresAcknowledgedReply.class);
        this.index = index;
        this.settings = settingsJson;
    }

    @Override
    public String getHttpMethod() {
        return HttpPut.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return index;
    }

    @Override
    public Object getPayload() {
        return settings;
    }

}
