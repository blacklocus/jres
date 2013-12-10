package com.blacklocus.jres.response.bulk;

import com.blacklocus.jres.response.JresJsonReply;
import com.blacklocus.jres.response.JresResult;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import org.codehaus.jackson.JsonNode;

import java.util.Iterator;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresBulkReply extends JresJsonReply {

    public Iterator<JresResult> getResults() {
        return Iterators.transform(node().get("items").iterator(), new Function<JsonNode, JresResult>() {
            @Override
            public JresResult apply(JsonNode result) {
                return null;
            }
        });
    }
}
