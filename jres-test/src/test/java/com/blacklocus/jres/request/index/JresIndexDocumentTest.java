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
import com.blacklocus.jres.model.search.Hits;
import com.blacklocus.jres.request.JresBulkable;
import com.blacklocus.jres.request.bulk.JresBulk;
import com.blacklocus.jres.request.search.JresSearch;
import com.blacklocus.jres.response.bulk.JresBulkReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.blacklocus.jres.response.index.JresIndexDocumentReply;
import com.google.common.collect.Iterables;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresIndexDocumentTest extends BaseJresTest {

    @Test
    public void testAutoId() {
        String index = "JresIndexDocumentTest_autoId".toLowerCase();
        String type = "test";

        Document document = new Document();
        JresIndexDocumentReply reply = jres.quest(new JresIndexDocument(index, type, document));

        Assert.assertTrue(reply.getOk());
        Assert.assertEquals(index, reply.getIndex());
        Assert.assertEquals(type, reply.getType());
        Assert.assertEquals("1", reply.getVersion());
    }

    @Test
    public void testWithId() {
        String index = "JresIndexDocumentTest_withId".toLowerCase();
        String type = "test";
        String id = "test_id";

        Document document = new Document();
        JresIndexDocumentReply reply = jres.quest(new JresIndexDocument(index, type, id, document));

        Assert.assertTrue(reply.getOk());
        Assert.assertEquals(index, reply.getIndex());
        Assert.assertEquals(type, reply.getType());
        Assert.assertEquals(id, reply.getId());
        Assert.assertEquals("1", reply.getVersion());

        jres.quest(new JresRefresh(index));
        Hits hits = jres.quest(new JresSearch(index, type)).getHits();
        Assert.assertEquals(new Integer(1), hits.getTotal());
        Assert.assertEquals(document.value, hits.getHits().get(0).getSourceAsType(Document.class).value);

        document.value = "lol";
        jres.quest(new JresIndexDocument(index, type, id, document));

        jres.quest(new JresRefresh(index));
        hits = jres.quest(new JresSearch(index, type)).getHits();
        Assert.assertEquals("should update existing, rather than create new doc", new Integer(1), hits.getTotal());
        Assert.assertEquals(document.value, hits.getHits().get(0).getSourceAsType(Document.class).value);
    }

    @Test(expected = JresErrorReplyException.class)
    public void testWithCreateOnly() {
        String index = "JresIndexDocumentTest_withCreateOnly".toLowerCase();
        String type = "test";
        String id = "test_id";

        Document document = new Document();
        JresIndexDocumentReply reply = jres.quest(new JresIndexDocument(index, type, id, document, true));
        Assert.assertTrue(reply.getOk());
        Assert.assertEquals(index, reply.getIndex());
        Assert.assertEquals(type, reply.getType());
        Assert.assertEquals(id, reply.getId());
        Assert.assertEquals("1", reply.getVersion());

        reply = jres.quest(new JresIndexDocument(index, type, id, document, false));
        Assert.assertTrue(reply.getOk());
        Assert.assertEquals(index, reply.getIndex());
        Assert.assertEquals(type, reply.getType());
        Assert.assertEquals(id, reply.getId());
        Assert.assertEquals("2", reply.getVersion());

        jres.quest(new JresIndexDocument(index, type, id, document, true));
    }

    @Test
    public void testBulkCreateOnly() {
        String index = "JresIndexDocumentTest_bulkCreateOnly".toLowerCase();
        String type = "test";
        String id1 = "test_id1";
        String id2 = "test_id2";

        Document document = new Document();
        JresBulkReply reply = jres.quest(new JresBulk(index, type, Arrays.<JresBulkable>asList(
                new JresIndexDocument(index, type, id1, document, true),
                new JresIndexDocument(index, type, id1, document, false)
        )));
        Assert.assertEquals(0, Iterables.size(reply.getErrorResults()));

        reply = jres.quest(new JresBulk(index, type, Arrays.<JresBulkable>asList(
                new JresIndexDocument(index, type, id2, document, true),
                new JresIndexDocument(index, type, id2, document, true)
        )));
        Assert.assertEquals(1, Iterables.size(reply.getErrorResults()));
    }

    static class Document {
        public String value = "wheee";
    }
}
