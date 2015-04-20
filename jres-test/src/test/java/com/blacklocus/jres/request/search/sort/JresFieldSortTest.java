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
package com.blacklocus.jres.request.search.sort;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.index.JresRefresh;
import com.blacklocus.jres.request.search.JresSearch;
import com.blacklocus.jres.request.search.JresSearchBody;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class JresFieldSortTest extends BaseJresTest {

    @Test
    public void test() {
        String index = "JresFieldSortTest.test".toLowerCase();
        String type = "test";

        List<Document> documents = Arrays.asList(
                new Document(5),
                new Document(25),
                new Document(105),
                new Document(42)
        );
        List<Document> correctOrder = Lists.newArrayList(Sets.newTreeSet(documents));

        for (Document document : documents) {
            jres.quest(new JresIndexDocument(index, type, document));
        }
        jres.quest(new JresRefresh(index));


        Assert.assertEquals(correctOrder, jres.quest(new JresSearch(index, type, new JresSearchBody().sort(
                new JresFieldSort("the_answer") // default asc
        ))).getHitsAsType(Document.class));
        Assert.assertEquals(correctOrder, jres.quest(new JresSearch(index, type, new JresSearchBody().sort(
                new JresFieldSort("the_answer").withOrder("asc")
        ))).getHitsAsType(Document.class));
        Assert.assertEquals(Lists.reverse(correctOrder), jres.quest(new JresSearch(index, type, new JresSearchBody().sort(
                new JresFieldSort("the_answer").withOrder("desc")
        ))).getHitsAsType(Document.class));
    }

    static class Document implements Comparable<Document> {
        public Integer the_answer;

        Document() {
        }

        Document(Integer the_answer) {
            this.the_answer = the_answer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Document document = (Document) o;

            if (the_answer != null ? !the_answer.equals(document.the_answer) : document.the_answer != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return the_answer != null ? the_answer.hashCode() : 0;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("the_answer", the_answer)
                    .toString();
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public int compareTo(Document o) {
            return this.the_answer.compareTo(o.the_answer);
        }
    }
}
