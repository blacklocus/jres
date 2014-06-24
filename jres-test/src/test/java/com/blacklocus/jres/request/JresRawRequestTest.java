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
package com.blacklocus.jres.request;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.JresImpl;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.index.JresRefresh;
import com.blacklocus.jres.request.search.JresSearch;
import com.blacklocus.jres.request.search.JresSearchBody;
import com.blacklocus.jres.request.search.query.JresBoolQuery;
import com.blacklocus.jres.request.search.query.JresMatchQuery;
import com.blacklocus.jres.response.JresJsonReply;
import com.blacklocus.jres.response.search.JresSearchReply;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpPost;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JresRawRequestTest extends BaseJresTest {

    @Test
    public void test() {
        String index = "JresRawRequestTest_test".toLowerCase();
        String type = "test";

        jres.quest(new JresIndexDocument(index, type, new Document("big")));
        jres.quest(new JresIndexDocument(index, type, new Document("small")));
        jres.quest(new JresRefresh(index));

        List<Document> hits = jres.quest(new JresRawRequest<JresSearchReply>(
                HttpPost.METHOD_NAME,
                JresPaths.slashedPath(index) + "_search",
                "{\"query\":{\"match_all\":{}}}",
                JresSearchReply.class
        )).getHitsAsType(Document.class);
        Assert.assertEquals(2, hits.size());

        hits = jres.quest(new JresRawRequest<JresSearchReply>(
                HttpPost.METHOD_NAME,
                JresPaths.slashedPath(index) + "_search",
                JresRawRequestTest.class.getResource("raw query.json"),
                JresSearchReply.class
        )).getHitsAsType(Document.class);
        Assert.assertEquals(1, hits.size());
        Assert.assertEquals("small", hits.get(0).compass);

        // Imagine this is much more complicated and we just want to insert values in a few key places.
        JresSearchBody searchBody = JresImpl.load(JresRawRequestTest.class.getResource("raw query base.json"), JresSearchBody.class);
        searchBody.getQuery(JresBoolQuery.class).should(
                new JresMatchQuery("compass", "big")
        );
        hits = jres.quest(new JresSearch(index, type, searchBody)).getHitsAsType(Document.class);
        Assert.assertEquals(1, hits.size());
        Assert.assertEquals("big", hits.get(0).compass);
    }

    @Test
    public void testBase() {
        jres.quest(new JresRawRequest<JresJsonReply>("get", "_search"));
    }

    static class Document {
        public String value = "yellow orange five seven";
        public String compass;

        Document() {
        }

        Document(String compass) {
            this.compass = compass;
        }
    }
}
