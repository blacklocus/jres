package com.blacklocus.jres.response.bulk;

import com.blacklocus.jres.model.bulk.Item;
import com.blacklocus.jres.response.JresJsonReply;
import com.blacklocus.jres.strings.ObjectMappers;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;
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

    public Iterable<JresBulkItemResult> getResults() {
        return Iterables.transform(node().get("items"), new Function<JsonNode, JresBulkItemResult>() {
            @Override
            public JresBulkItemResult apply(JsonNode resultEntry) {
                assert resultEntry.size() == 1;
                Map.Entry<String, JsonNode> result = resultEntry.getFields().next();
                try {
                    Item item = ObjectMappers.NORMAL.readValue(result.getValue(), Item.class);
                    return new JresBulkItemResult(result.getKey(), item);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public Iterable<JresBulkItemResult> getErrorResults() {
        return Iterables.filter(getResults(), new Predicate<JresBulkItemResult>() {
            @Override
            public boolean apply(JresBulkItemResult input) {
                return true;
            }
        });
    }
}
