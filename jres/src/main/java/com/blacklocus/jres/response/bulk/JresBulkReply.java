/**
 * Copyright 2013 BlackLocus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blacklocus.jres.response.bulk;

import com.blacklocus.jres.model.bulk.Item;
import com.blacklocus.jres.response.JresJsonReply;
import com.blacklocus.jres.strings.ObjectMappers;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

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
                Map.Entry<String, JsonNode> result = resultEntry.fields().next();
                Item item = ObjectMappers.fromJson(result.getValue(), Item.class);
                return new JresBulkItemResult(result.getKey(), item);
            }
        });
    }

    public Iterable<JresBulkItemResult> getErrorResults() {
        return Iterables.filter(getResults(), new Predicate<JresBulkItemResult>() {
            @Override
            public boolean apply(JresBulkItemResult input) {
                return input.getResult().hasError();
            }
        });
    }
}
