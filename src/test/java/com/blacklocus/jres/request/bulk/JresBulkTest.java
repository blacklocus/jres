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
package com.blacklocus.jres.request.bulk;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.JresBulkable;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.index.JresRefresh;
import com.blacklocus.jres.request.mapping.JresPutMapping;
import com.blacklocus.jres.request.search.JresSearch;
import com.blacklocus.jres.response.bulk.JresBulkItemResult;
import com.blacklocus.jres.response.bulk.JresBulkReply;
import com.blacklocus.jres.response.search.JresSearchReply;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresBulkTest extends JresTest {

    @Test
    public void testHappy() {
        String index = "JresBulkRequestTest_happy".toLowerCase();
        String type = "test";

        jres.quest(new JresCreateIndex(index));
        jres.quest(new JresPutMapping(index, type));

        JresBulkReply bulkResponse = jres.quest(new JresBulk(index, type, Arrays.<JresBulkable>asList(
                new JresIndexDocument(index, type, new Document("one")),
                new JresIndexDocument(index, type, new Document("two"))
        )));
        Assert.assertEquals(2, bulkResponse.getItems().size());
        for (JresBulkItemResult bulkItemResult : bulkResponse.getResults()) {
            Assert.assertEquals(JresBulkItemResult.ACTION_CREATE, bulkItemResult.getAction());
        }

        jres.quest(new JresRefresh(index));
        JresSearchReply searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals(new Integer(2), searchReply.getHits().getTotal());
    }

    @Test
    public void testError() {
        String index = "JresBulkRequestTest_error".toLowerCase();
        String type = "test";

        jres.quest(new JresCreateIndex(index));
        jres.quest(new JresPutMapping(index, type));

        JresBulkReply bulkResponse = jres.quest(new JresBulk(index, type, Arrays.<JresBulkable>asList(
                new JresIndexDocument(index, type, new Document2()),
                // Try to index a document which is inconvertible with the generated schema.
                new JresIndexDocument(index, type, new Document("one"))
        )));
        Assert.assertEquals(2, bulkResponse.getItems().size());
        Iterator<JresBulkItemResult> results = bulkResponse.getResults().iterator();

        JresBulkItemResult ok = results.next();
        Assert.assertEquals(JresBulkItemResult.ACTION_CREATE, ok.getAction());
        Assert.assertFalse(ok.getResult().hasError());

        JresBulkItemResult notOk = results.next();
        Assert.assertEquals(JresBulkItemResult.ACTION_CREATE, notOk.getAction());
        Assert.assertTrue(notOk.getResult().hasError());

        jres.quest(new JresRefresh(index));
        JresSearchReply searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals(new Integer(1), searchReply.getHits().getTotal());


        bulkResponse = jres.quest(new JresBulk(index, type, Arrays.<JresBulkable>asList(
                new JresIndexDocument(index, type, new Document("one")),
                new JresIndexDocument(index, type, new Document2())
        )));
        Assert.assertEquals(2, bulkResponse.getItems().size());
    }

    static class Document {
        public String value;

        Document(String value) {
            this.value = value;
        }
    }

    static class Document2 {
        public int value = 5;
    }
}
