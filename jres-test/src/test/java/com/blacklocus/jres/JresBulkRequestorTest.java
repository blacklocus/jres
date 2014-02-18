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
package com.blacklocus.jres;

import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.index.JresRefresh;
import com.blacklocus.jres.request.index.JresUpdateDocument;
import com.blacklocus.jres.request.search.JresSearch;
import com.blacklocus.jres.response.search.JresSearchReply;
import com.google.common.base.Objects;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class JresBulkRequestorTest extends BaseJresTest {

    private static final Logger LOG = LoggerFactory.getLogger(JresBulkRequestorTest.class);

    @Test
    public void testBulkRequestor() throws InterruptedException, ExecutionException, IOException {

        // best for this to be a multiple of 4
        int batchSize = 4;

        String index = "JresBulkRequestorTest.testBulkRequestor".toLowerCase();
        String type = "test";

        // Specify default index, but not default type.
        JresBulkRequestor bulkRequestor = new JresBulkRequestor(batchSize, 1000, 1, index, jres).start();
        List<Future<?>> futures = new ArrayList<Future<?>>();

        // hang on to some docs so we can issue update operations
        List<Document> docs = new ArrayList<Document>(batchSize);


        for (int i = 0; i < batchSize; i++) {
            Document doc = new Document();
            docs.add(doc);
            futures.add(bulkRequestor.put(new JresIndexDocument(index, type, doc.id, doc)));
        }
        // batch filled, buffer should flush

        for (int i = 0; i < batchSize; i++) {
            Document doc = docs.get(i);
            doc.name = RandomStringUtils.randomAscii(8) + ".mp3";
            futures.add(bulkRequestor.put(new JresUpdateDocument(index, type, doc.id, doc, false, 0)));
        }
        // batch filled, buffer should flush

        for (int i = 0; i < batchSize / 4; i++) {
            // add 2 mixed operations
            Document newDoc = new Document();
            docs.add(newDoc);
            futures.add(bulkRequestor.put(new JresIndexDocument(index, type, newDoc)));

            Document updateDoc = docs.get(i);
            updateDoc.name = "Hi, my name is " + updateDoc.name;
            // null index for fun, since it was specified in the JresBulkRequestor constructor. This should still work.
            futures.add(bulkRequestor.put(new JresUpdateDocument(null, type, updateDoc.id, updateDoc)));
        }
        // Some broken operations, conflicting schema.
        for (int i = 0; i < batchSize / 4; i++) {
            futures.add(bulkRequestor.put(new JresIndexDocument(index, type, new ConflictingDocument())));
        }
        // Batch not filled (actually only 3/4 full). Closing the requestor should block until buffers are flushed.
        bulkRequestor.close();

        jres.quest(new JresRefresh(index));
        JresSearchReply searchReply = jres.quest(new JresSearch(index));
        Assert.assertEquals(docs.size(), searchReply.getHits().getTotal().intValue());


        int validFutures = futures.size() - batchSize / 4;
        int failedFutures = batchSize / 4;
        Assert.assertEquals("All futures accounted for (test logic itself)", futures.size(), validFutures + failedFutures);
        for (int i = 0; i < validFutures; i++) {
            futures.get(i).get(); // successful
        }
        for (int i = validFutures; i < futures.size(); i++) {
            try {
                futures.get(i).get();
                Assert.fail("Future should have excepted");
            } catch (Exception e) {
                // good
                LOG.debug("Failed future was expected and had this exception", e);
            }
        }
    }

    static class Document {

        public String id = UUID.randomUUID().toString();
        public String name = RandomStringUtils.randomAlphabetic(8);
        public Integer the_number = 8;

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("id", id)
                    .add("name", name)
                    .add("the_number", the_number)
                    .toString();
        }
    }

    static class ConflictingDocument {
        public String the_number = "ha ha not a number";
    }
}
