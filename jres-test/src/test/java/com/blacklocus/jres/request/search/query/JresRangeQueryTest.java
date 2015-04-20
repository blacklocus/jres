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
package com.blacklocus.jres.request.search.query;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.index.JresRefresh;
import com.blacklocus.jres.request.mapping.JresPutMapping;
import com.blacklocus.jres.request.search.JresSearch;
import com.blacklocus.jres.request.search.JresSearchBody;
import com.blacklocus.jres.response.search.JresSearchReply;
import com.blacklocus.jres.strings.ObjectMappers;
import org.elasticsearch.common.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static com.google.common.collect.ImmutableMap.of;

public class JresRangeQueryTest extends BaseJresTest {

    @Test
    public void testNumeric() {
        String index = "JresRangeQueryTest.testNumeric".toLowerCase();
        String type = "test";

        jres.quest(new JresIndexDocument(index, type, new NumericDocument("red balloon", 1)));
        jres.quest(new JresIndexDocument(index, type, new NumericDocument("yellow balloon", 2)));
        jres.quest(new JresIndexDocument(index, type, new NumericDocument("yellow car", 3)));
        jres.quest(new JresRefresh(index));
        JresSearchReply quest = jres.quest(new JresSearch(index, type));
        Assert.assertEquals((Object) 3L, quest.getHits().getTotal());

        JresSearchReply searchReply = jres.quest(new JresSearch(index, type, new JresSearchBody()
                .query(new JresRangeQuery("value").gte(2))));
        Assert.assertEquals((Object) 2L, searchReply.getHits().getTotal());
        for (NumericDocument document : searchReply.getHitsAsType(NumericDocument.class)) {
            Assert.assertTrue(document.description.contains("yellow"));
        }
    }

    @Test
    public void testDate() {
        String index = "JresRangeQueryTest.testDate".toLowerCase();
        String type = "test";

        jres.quest(new JresCreateIndex(index, type));
        String mappingJson = ObjectMappers.toJson(of(
                type, of(
                        "properties", of(
                                "value", of(
                                        "type", "date"
                                )
                        )
                )
        ));
        jres.quest(new JresPutMapping(index, type, mappingJson));

        jres.quest(new JresIndexDocument(index, type, new DateDocument("red balloon", DateTime.parse("2014-01-01T01:01:00").toDate())));
        jres.quest(new JresIndexDocument(index, type, new DateDocument("yellow balloon", DateTime.parse("2014-01-01T01:02:00").toDate())));
        jres.quest(new JresIndexDocument(index, type, new DateDocument("yellow car", DateTime.parse("2014-01-01T01:03:00").toDate())));
        jres.quest(new JresRefresh(index));
        JresSearchReply quest = jres.quest(new JresSearch(index, type));
        Assert.assertEquals((Object) 3L, quest.getHits().getTotal());

        JresSearchReply searchReply = jres.quest(new JresSearch(index, type, new JresSearchBody()
                .query(new JresRangeQuery("value").lte("2014-01-01T01:02:00"))));
        Assert.assertEquals((Object) 2L, searchReply.getHits().getTotal());
        for (NumericDocument document : searchReply.getHitsAsType(NumericDocument.class)) {
            Assert.assertTrue(document.description.contains("balloon"));
        }
    }

    static class NumericDocument {
        public String description;
        public Integer value;

        NumericDocument() {
        }

        NumericDocument(String description, Integer value) {
            this.description = description;
            this.value = value;
        }
    }

    static class DateDocument {
        public String description;
        public Date value;

        DateDocument() {
        }

        DateDocument(String description, Date value) {
            this.description = description;
            this.value = value;
        }
    }
}
