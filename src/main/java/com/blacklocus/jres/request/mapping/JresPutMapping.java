package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.common.JresAcknowledgedReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpPut;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-put-mapping.html#indices-put-mapping">Put Mapping API</a>
 * <p>
 * Can throw {@link JresErrorReplyException}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresPutMapping extends JresJsonRequest<JresAcknowledgedReply> {

    private final String index;
    private final String type;
    private final String mappingJson;

    public JresPutMapping(String index, String type) {
        this(index, type, String.format("{\"%s\":{}}", type));
    }

    public JresPutMapping(String index, String type, String mappingJson) {
        super(JresAcknowledgedReply.class);
        this.index = index;
        this.type = type;
        this.mappingJson = mappingJson;
    }

    @Override
    public String getHttpMethod() {
        return HttpPut.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(index) + JresPaths.slashed(type) + "_mapping";
    }

    @Override
    public Object getPayload() {
        return mappingJson;
    }

}
