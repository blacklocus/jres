/**
 * Copyright 2015 BlackLocus
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class JresUpdateDocumentScriptTest extends BaseJresTest {

    @Test(expected = JresErrorReplyException.class)
    public void testPartialUpdatesNoUpsert() {
        String index = "JresUpdateDocumentScriptTest.testPartialUpdatesNoUpsert".toLowerCase();
        String type = "test";
        String id1 = "Fotel 6";

        // Script doesn't matter because document does not exist and this is not an upsert.
        jres.quest(new JresUpdateDocumentScript(index, type, id1, ""));
    }

    @Test
    public void testPartialUpdatesWithUpsertOption() {
        String index = "JresUpdateDocumentScriptTest.testPartialUpdatesWithUpsertOption".toLowerCase();
        String type = "test";
        String id1 = "horchata";
        String id2 = "margarita";

        Object updateDoc1WithFood = ImmutableMap.of("description", Arrays.asList("Es horchata"));
        Object updateDoc2WithFood = ImmutableMap.of("description", Arrays.asList("Es margarita"));
        // Scripts don't matter, because this should fall back to insert since the docs don't exist.
        jres.quest(new JresUpdateDocumentScript(index, type, id1, "", Collections.<String, Object>emptyMap(), updateDoc1WithFood, null));
        jres.quest(new JresUpdateDocumentScript(index, type, id2, "", Collections.<String, Object>emptyMap(), updateDoc2WithFood, null));
        jres.quest(new JresRefresh(index));

        JresSearchReply searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals((Object) 2L, searchReply.getHits().getTotal());


        String updateDoc1Script = "ctx._source.description += description";
        Map<String, String> updateDoc1With2ndDesc = ImmutableMap.of("description", "¡Sí, es final!");
        jres.quest(new JresUpdateDocumentScript(index, type, id1, updateDoc1Script, updateDoc1With2ndDesc));
        jres.quest(new JresRefresh(index));

        searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals("Still 2 documents", (Object) 2L, searchReply.getHits().getTotal());

        Map<String, List<String>> doc1 = jres.quest(new JresGetDocument(index, type, id1))
                .getSourceAsType(new TypeReference<Map<String, List<String>>>() {});
        Assert.assertEquals(ImmutableMap.of(
                "description", Arrays.asList("Es horchata", "¡Sí, es final!")
        ), doc1);

        Map<String, List<String>> doc2 = jres.quest(new JresGetDocument(index, type, id2)).getSourceAsType(
                new TypeReference<Map<String, List<String>>>() {});
        Assert.assertEquals(ImmutableMap.of(
                "description", Arrays.asList("Es margarita")
        ), doc2);
    }

    @Test
    public void testBulkPartialUpdates() {
        String index = "JresUpdateDocumentScriptTest.testBulkPartialUpdates".toLowerCase();
        String type = "test";
        String id1 = "horchata";
        String id2 = "margarita";

        Map<String, List<String>> updateDoc1WithFood = ImmutableMap.of("description", Arrays.asList("Es horchata"));
        Map<String, List<String>> updateDoc2WithFood = ImmutableMap.of("description", Arrays.asList("Es margarita"));
        // Scripts don't matter, because this should fall back to insert since the docs don't exist.
        jres.quest(new JresBulk(index, type, Arrays.<JresBulkable>asList(
                new JresUpdateDocumentScript(index, type, id1, "", Collections.<String, Object>emptyMap(), updateDoc1WithFood, null),
                new JresUpdateDocumentScript(index, type, id2, "", Collections.<String, Object>emptyMap(), updateDoc2WithFood, null)
        )));
        jres.quest(new JresRefresh(index));

        JresSearchReply searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals((Object) 2L, searchReply.getHits().getTotal());


        String updateDoc1Script = "ctx._source.description += description";
        Map<String, String> updateDoc1With2ndDesc = ImmutableMap.of("description", "¡Sí, es final!");
        jres.quest(new JresBulk(index, type, Arrays.<JresBulkable>asList(
                // results in update of existing doc
                new JresUpdateDocumentScript(index, type, id1, updateDoc1Script, updateDoc1With2ndDesc),
                // results in insert of new doc
                new JresUpdateDocumentScript(index, type, "id3", "", Collections.<String, Object>emptyMap(), ImmutableMap.of(
                        "description", Arrays.asList("the 3rd document")
                ), null)
        )));
        jres.quest(new JresRefresh(index));

        searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals("3 total documents", (Object) 3L, searchReply.getHits().getTotal());


        Map<String, List<String>> doc1 = jres.quest(new JresGetDocument(index, type, id1))
                .getSourceAsType(new TypeReference<Map<String, List<String>>>() {});
        Assert.assertEquals(ImmutableMap.of(
                "description", Arrays.asList("Es horchata", "¡Sí, es final!")
        ), doc1);

        Map<String, List<String>> doc2 = jres.quest(new JresGetDocument(index, type, id2))
                .getSourceAsType(new TypeReference<Map<String, List<String>>>() {});
        Assert.assertEquals(ImmutableMap.of(
                "description", Arrays.asList("Es margarita")
        ), doc2);
    }

    @Test(expected = ExecutionException.class)
    public void testRetryOnConflictExpectError() throws InterruptedException, ExecutionException {
        final String index = "JresUpdateDocumentScriptTest.testRetryOnConflictExpectError".toLowerCase();
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
                        jres.quest(new JresUpdateDocumentScript(index, type, id, "ctx._source.value += 1",
                                null, ImmutableMap.of("value", 0), null));
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
        final String index = "JresUpdateDocumentScriptTest.testRetryOnConflict".toLowerCase();
        final String type = "test";
        final String id = "warzone";

        final AtomicInteger total = new AtomicInteger();
        final AtomicReference<String> error = new AtomicReference<String>();
        final Random random = new Random(System.currentTimeMillis());

        final int numThreads = 16, numIterations = 100;

        ExecutorService x = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; i++) {
            x.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int j = 0; j < numIterations; j++) {
                            int increment = random.nextInt(5);
                            total.addAndGet(increment);
                            JresUpdateDocumentScript req = new JresUpdateDocumentScript(index, type, id, "ctx._source.value += increment",
                                    ImmutableMap.of("increment", increment), ImmutableMap.of("value", increment), null);
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
        Assert.assertEquals("All increments should have gotten committed", (Object) total.get(), doc.get("value"));
        Assert.assertEquals("Should have been numThreads * numIterations versions committed",
                (Object) (numThreads * numIterations), getReply.getVersion());
    }
}
