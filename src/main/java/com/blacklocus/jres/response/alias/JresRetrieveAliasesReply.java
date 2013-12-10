package com.blacklocus.jres.response.alias;

import com.blacklocus.jres.request.alias.JresRetrieveAliases;
import com.blacklocus.jres.response.JresJsonReply;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Response for {@link JresRetrieveAliases}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresRetrieveAliasesReply extends JresJsonReply {

    public List<String> getIndexes() {
        return Lists.newArrayList(node().getFieldNames());
    }

    public List<String> getAliases(String index) {
        return Lists.newArrayList(node().get(index).get("aliases").getFieldNames());
    }
}
