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
package com.blacklocus.jres;

import com.blacklocus.jres.model.search.Hit;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.request.index.JresFlush;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.mapping.JresPutMapping;
import com.blacklocus.jres.request.search.JresSearch;
import com.blacklocus.jres.request.search.JresSearchBody;
import com.blacklocus.jres.request.search.query.JresBoolQuery;
import com.blacklocus.jres.request.search.query.JresMatchQuery;
import com.blacklocus.jres.response.search.JresSearchReply;
import com.google.common.base.Supplier;
import org.junit.Test;

public class ReadmeTest extends BaseJresTest {

    @Test
    public void main() {

        Supplier<String> host = this.jres.getHosts();

        // The rest should be identical to README snippet.

        // Setup an index with a mapping for documents of type MyText.
        String index = "readme_test_index";
        String type = "definition";
        String mapping = "{" +
                "  \"definition\": {" +
                "    \"properties\": {" +
                "      \"body\": {" +
                "        \"type\": \"string\"" +
                "      }" +
                "    }" +
                "  }" +
                "}";

        Jres jres = new Jres(host);
        jres.quest(new JresCreateIndex(index));
        jres.quest(new JresPutMapping(index, type, mapping));

        // Index two documents (definitions via Google)
        Definition cat = new Definition("cat",
                "a small domesticated carnivorous mammal " +
                        "with soft fur, a short snout, and retractile claws. It is " +
                        "widely kept as a pet or for catching mice, and many breeds " +
                        "have been developed.");
        Definition dog = new Definition("dog",
                "a domesticated carnivorous mammal that " +
                        "typically has a long snout, an acute sense of smell, and a " +
                        "barking, howling, or whining voice. It is widely kept as a pet " +
                        "or for work or field sports.");
        jres.quest(new JresIndexDocument(index, type, cat));
        jres.quest(new JresIndexDocument(index, type, dog));

        // Do some querying
        JresMatchQuery retractileQuery = new JresMatchQuery("body", "retractile");
        JresMatchQuery mammalQuery = new JresMatchQuery("body", "mammal");
        JresMatchQuery alligatorQuery = new JresMatchQuery("body", "alligator");
        JresBoolQuery retractileAndMammalQuery = new JresBoolQuery()
                .must(retractileQuery, mammalQuery);

        // We need to flush the queue in ElasticSearch otherwise our recently
        // submitted documents may not yet be indexed.
        jres.quest(new JresFlush(index));

        JresSearchReply reply;

        System.out.println("Searching 'retractile'");
        reply = jres.quest(new JresSearch(index, type, new JresSearchBody()
                .query(retractileQuery)));
        for (Hit hit : reply.getHits().getHits()) {
            Definition definition = hit.getSourceAsType(Definition.class);
            System.out.println("  Found " + definition.term);
        }

        System.out.println("Searching 'mammal'");
        reply = jres.quest(new JresSearch(index, type, new JresSearchBody()
                .query(mammalQuery)));
        for (Hit hit : reply.getHits().getHits()) {
            Definition definition = hit.getSourceAsType(Definition.class);
            System.out.println("  Found " + definition.term);
        }

        System.out.println("Searching 'alligator'");
        reply = jres.quest(new JresSearch(index, type, new JresSearchBody()
                .query(alligatorQuery)));
        if (reply.getHits().getTotal() == 0) {
            System.out.println("  Nothing found about alligators");
        }

        System.out.println("Searching 'retractile' and 'mammal'");
        reply = jres.quest(new JresSearch(index, type, new JresSearchBody()
                .query(retractileAndMammalQuery)));
        for (Hit hit : reply.getHits().getHits()) {
            Definition definition = hit.getSourceAsType(Definition.class);
            System.out.println("  Found " + definition.term);
        }
    }

    // This must be (de)serializable by Jackson. I have chosen to go with
    // public fields, and provided a non-private default constructor.
    // This is just one of many ways to enable Jackson (de)serialization.
    static class Definition {
        public String term;
        public String body;

        Definition() {
        }

        public Definition(String term, String body) {
            this.term = term;
            this.body = body;
        }

        @Override
        public String toString() {
            return term + ": " + body;
        }
    }
}
