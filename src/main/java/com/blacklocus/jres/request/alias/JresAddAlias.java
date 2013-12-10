package com.blacklocus.jres.request.alias;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.common.JresAcknowledgedReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpPut;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-aliases.html#alias-adding">Add Single Index Alias API</a>
 * <p>
 * Can throw {@link JresErrorReplyException}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresAddAlias extends JresJsonRequest<JresAcknowledgedReply> {

    private final String index;
    private final String alias;

    /**
     * @param index the actual index
     * @param alias another name by which the actual index may be addressed
     */
    public JresAddAlias(String index, String alias) {
        super(JresAcknowledgedReply.class);
        this.index = index;
        this.alias = alias;
    }

    @Override
    public String getHttpMethod() {
        return HttpPut.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(index) + "_alias/" + alias;
    }

    @Override
    public Object getPayload() {
        return null;
    }

}
