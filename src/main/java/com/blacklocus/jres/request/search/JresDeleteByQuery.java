package com.blacklocus.jres.request.search;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.request.search.query.JresQuery;
import com.blacklocus.jres.response.common.JresIndicesReply;
import com.blacklocus.jres.strings.JresPaths;
import com.google.common.collect.ImmutableMap;
import org.apache.http.client.methods.HttpDelete;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresDeleteByQuery extends JresJsonRequest<JresIndicesReply> {

    private @Nullable String index;
    private @Nullable String type;
    /** Single entry from {@link JresQuery#queryType()} to the JresQuery itself. */
    private final Map<String, JresQuery> query;

    public JresDeleteByQuery(@Nullable String index, JresQuery query) {
        this(index, null, query);
    }

    public JresDeleteByQuery(@Nullable String index, @Nullable String type, JresQuery query) {
        super(JresIndicesReply.class);
        this.index = index;
        this.type = type;
        this.query = ImmutableMap.of(query.queryType(), query);
    }

    @Override
    public String getHttpMethod() {
        return HttpDelete.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(index) + JresPaths.slashed(type) + "_query";
    }

    @Override
    public Object getPayload() {
        return query;
    }
}
