package com.blacklocus.jres.request.alias;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.alias.JresRetrieveAliasesReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpGet;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-aliases.html#alias-adding">Add Single Index Alias API</a>
 * <p>
 * Can throw {@link JresErrorReplyException}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresRetrieveAliases extends JresJsonRequest<JresRetrieveAliasesReply> {

    private final String indexPattern;
    private final String aliasPattern;

    /**
     * @param indexPattern which appears to accept any number of '*' wildcards interspersed with characters and multiple
     *                     expressions separated by ','
     * @param aliasPattern which appears to accept any number of '*' wildcards interspersed with characters and multiple
     *                     expressions separated by ','
     */
    public JresRetrieveAliases(String indexPattern, String aliasPattern) {
        super(JresRetrieveAliasesReply.class);
        this.indexPattern = indexPattern;
        this.aliasPattern = aliasPattern;
    }

    @Override
    public String getHttpMethod() {
        return HttpGet.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(indexPattern) + "_alias/" + aliasPattern;
    }

    @Override
    public Object getPayload() {
        return null;
    }

}
