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
package com.blacklocus.jres.request.index;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.request.JresBulkable;
import com.blacklocus.jres.request.bulk.JresBulk;
import com.blacklocus.jres.request.document.JresGetDocument;
import com.blacklocus.jres.request.search.JresSearch;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.blacklocus.jres.response.document.JresGetDocumentReply;
import com.blacklocus.jres.response.search.JresSearchReply;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

public class JresUpdateDocumentTest extends BaseJresTest {

    @Test(expected = JresErrorReplyException.class)
    public void testPartialUpdatesNoUpsert() {
        String index = "JresUpdateDocumentTest.testPartialUpdatesNoUpsert".toLowerCase();
        String type = "test";
        String id1 = "Fotel 6";

        jres.quest(new JresUpdateDocument(index, type, id1, ImmutableMap.of(
                "rating", 3
        ), false));
    }

    @Test
    public void testPartialUpdatesWithUpsertOption() {
        String index = "JresUpdateDocumentTest.testPartialUpdatesWithUpsertOption".toLowerCase();
        String type = "test";
        String id1 = "horchata";
        String id2 = "margarita";

        Object updateDoc1WithFood = ImmutableMap.of("description", "Es horchata");
        Object updateDoc2WithFood = ImmutableMap.of("description", "Es margarita");
        jres.quest(new JresUpdateDocument(index, type, id1, updateDoc1WithFood));
        jres.quest(new JresUpdateDocument(index, type, id2, updateDoc2WithFood));
        jres.quest(new JresRefresh(index));

        JresSearchReply searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals((Object) 2L, searchReply.getHits().getTotal());


        Object updateDoc1WithDeliciousness = ImmutableMap.of("delicious", true);
        jres.quest(new JresUpdateDocument(index, type, id1, updateDoc1WithDeliciousness));
        jres.quest(new JresRefresh(index));

        searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals("Still 2 documents", (Object) 2L, searchReply.getHits().getTotal());


        JresGetDocumentReply getDocumentReply = jres.quest(new JresGetDocument(index, type, id1));
        Map<String, Object> doc1 = getDocumentReply.getSourceAsType(new TypeReference<Map<String, Object>>() {
        });
        Assert.assertEquals((Map) ImmutableMap.of(
                "description", "Es horchata",
                "delicious", true
        ), doc1);

        getDocumentReply = jres.quest(new JresGetDocument(index, type, id2));
        Map<String, Object> doc2 = getDocumentReply.getSourceAsType(new TypeReference<Map<String, Object>>() {
        });
        Assert.assertEquals((Map) ImmutableMap.of(
                "description", "Es margarita"
        ), doc2);
    }

    @Test
    public void testBulkPartialUpdates() {
        String index = "JresUpdateDocumentTest.testBulkPartialUpdates".toLowerCase();
        String type = "test";
        String id1 = "horchata";
        String id2 = "margarita";

        Object updateDoc1WithFood = ImmutableMap.of("description", "Es horchata");
        Object updateDoc2WithFood = ImmutableMap.of("description", "Es margarita");
        jres.quest(new JresBulk(index, type, Arrays.<JresBulkable>asList(
                new JresUpdateDocument(index, type, id1, updateDoc1WithFood),
                new JresUpdateDocument(index, type, id2, updateDoc2WithFood)
        )));
        jres.quest(new JresRefresh(index));

        JresSearchReply searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals((Object) 2L, searchReply.getHits().getTotal());


        Object updateDoc1WithDeliciousness = ImmutableMap.of("delicious", true);
        jres.quest(new JresBulk(index, type, Arrays.<JresBulkable>asList(
                new JresUpdateDocument(index, type, id1, updateDoc1WithDeliciousness),
                new JresUpdateDocument(index, type, "id3", ImmutableMap.of(
                        "description", "the 3rd document"
                ))
        )));
        jres.quest(new JresRefresh(index));

        searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals("3 total documents", (Object) 3L, searchReply.getHits().getTotal());


        JresGetDocumentReply getDocumentReply = jres.quest(new JresGetDocument(index, type, id1));
        Map<String, Object> doc1 = getDocumentReply.getSourceAsType(new TypeReference<Map<String, Object>>() {
        });
        Assert.assertEquals((Map) ImmutableMap.of(
                "description", "Es horchata",
                "delicious", true
        ), doc1);

        getDocumentReply = jres.quest(new JresGetDocument(index, type, id2));
        Map<String, Object> doc2 = getDocumentReply.getSourceAsType(new TypeReference<Map<String, Object>>() {
        });
        Assert.assertEquals((Map) ImmutableMap.of(
                "description", "Es margarita"
        ), doc2);
    }
}
