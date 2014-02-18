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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class JresUpdateDocumentTest extends BaseJresTest {

    @Test(expected = JresErrorReplyException.class)
    public void testPartialUpdatesNoUpsert() {
        String index = "JresUpdateDocumentTest.testPartialUpdatesNoUpsert".toLowerCase();
        String type = "test";
        String id1 = "Fotel 6";

        jres.quest(new JresUpdateDocument(index, type, id1, ImmutableMap.of(
                "rating", 3
        ), false, 0));
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

    @Test(expected = ExecutionException.class)
    public void testRetryOnConflictExpectError() throws InterruptedException, ExecutionException {
        final String index = "JresUpdateDocumentTest.testRetryOnConflictExpectError".toLowerCase();
        final String type = "test";
        final String id = "warzone";

        final AtomicReference<String> error = new AtomicReference<String>();
        final int numThreads = 16, numIterations = 100;

        ExecutorService x = Executors.newFixedThreadPool(numThreads);
        List<Future<?>> futures = new ArrayList<Future<?>>(numThreads);
        for (int i = 0; i < numThreads; i++) {
            futures.add(x.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (int j = 0; j < numIterations; j++) {
                        jres.quest(new JresUpdateDocument(index, type, id, ImmutableMap.of("value", 0)));
                    }
                    return null;
                }
            }));
        }
        x.shutdown();
        x.awaitTermination(1, TimeUnit.MINUTES);

        for (Future<?> future : futures) {
            // expecting a conflict exception from ElasticSearch
            future.get();
        }
    }

    @Test
    public void testRetryOnConflict() throws InterruptedException {
        final String index = "JresUpdateDocumentTest.testRetryOnConflict".toLowerCase();
        final String type = "test";
        final String id = "warzone";

        final AtomicReference<String> error = new AtomicReference<String>();

        final int numThreads = 16, numIterations = 100;

        ExecutorService x = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; i++) {
            x.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int j = 0; j < numIterations; j++) {
                            JresUpdateDocument req = new JresUpdateDocument(index, type, id, ImmutableMap.of("value", 0));
                            req.setRetryOnConflict(numIterations * 10);
                            jres.quest(req);
                        }
                    } catch (Exception e) {
                        error.set(e.getMessage());
                    }
                }
            });
        }
        x.shutdown();
        x.awaitTermination(1, TimeUnit.MINUTES);

        Assert.assertNull("With so many retries, all of these should have gotten through without conflict error", error.get());
        jres.quest(new JresRefresh(index));
        JresGetDocumentReply getReply = jres.quest(new JresGetDocument(index, type, id));
        Map<String, Integer> doc = getReply.getSourceAsType(new TypeReference<Map<String, Integer>>() {});
        Assert.assertEquals("Should have been numThreads * numIterations versions committed",
                (Object) (numThreads * numIterations), getReply.getVersion());
    }
}
