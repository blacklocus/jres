package com.blacklocus.jres.response.bulk;

import com.blacklocus.jres.model.bulk.Item;
import com.blacklocus.jres.response.JresJsonReply;
import com.blacklocus.jres.response.JresResult;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import org.codehaus.jackson.JsonNode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresBulkReply extends JresJsonReply {

    private Integer took;
    private List<Map<String, Item>> items;

    public List<String> keys() {
        return Lists.transform(items, new Function<Map<String, Item>, String>() {
            @Override
            public String apply(Map<String, Item> action) {
                assert action.size() == 1;
                return action.keySet().iterator().next();
            }
        });
    }

    public Integer getTook() {
        return took;
    }

    public List<Map<String, Item>> getItems() {
        return items;
    }

    public Iterator<JresResult> getResults() {
        return Iterators.transform(node().get("items").iterator(), new Function<JsonNode, JresResult>() {
            @Override
            public JresResult apply(JsonNode result) {
                return null;
            }
        });
    }
}
