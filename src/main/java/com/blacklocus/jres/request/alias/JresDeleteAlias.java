package com.blacklocus.jres.request.alias;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.common.JresAcknowledgedReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpDelete;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-aliases.html#deleting">Delete Single Index Alias API</a>
 * <p>
 * This command will return successful even if the alias didn't exist. The index must still exist.
 * <p>
 * Can throw {@link JresErrorReplyException}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresDeleteAlias extends JresJsonRequest<JresAcknowledgedReply> {

    private final String index;
    private final String alias;

    /**
     * @param index the actual index
     * @param alias another name by which the actual index may be addressed
     */
    public JresDeleteAlias(String index, String alias) {
        super(JresAcknowledgedReply.class);
        this.index = index;
        this.alias = alias;
    }

    @Override
    public String getHttpMethod() {
        return HttpDelete.METHOD_NAME;
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
