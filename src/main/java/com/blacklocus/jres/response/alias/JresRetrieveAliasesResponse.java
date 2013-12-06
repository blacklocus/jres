package com.blacklocus.jres.response.alias;

import com.blacklocus.jres.request.alias.JresRetrieveAliasesRequest;
import com.blacklocus.jres.response.JresJsonNodeResponse;
import com.google.common.collect.Lists;
import org.codehaus.jackson.JsonNode;

import java.util.List;

/**
 * Response for {@link JresRetrieveAliasesRequest}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresRetrieveAliasesResponse extends JresJsonNodeResponse {

    public JresRetrieveAliasesResponse(JsonNode node) {
        super(node);
    }

    public List<String> getIndexes() {
        return Lists.newArrayList(asNode().getFieldNames());
    }

    public List<String> getAliases(String index) {
        return Lists.newArrayList(asNode().get(index).get("aliases").getFieldNames());
    }
}
