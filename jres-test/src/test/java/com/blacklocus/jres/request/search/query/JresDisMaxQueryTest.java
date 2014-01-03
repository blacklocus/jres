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
package com.blacklocus.jres.request.search.query;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.index.JresRefresh;
import com.blacklocus.jres.request.search.JresSearch;
import com.blacklocus.jres.request.search.JresSearchBody;
import com.blacklocus.jres.response.search.JresSearchReply;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresDisMaxQueryTest extends BaseJresTest {

    @Test
    public void test() {
        String index = "JresDisMaxQueryTest.test".toLowerCase();
        String type = "test";

        jres.quest(new JresIndexDocument(index, type, new Document("red balloon")));
        jres.quest(new JresIndexDocument(index, type, new Document("yellow balloon")));
        jres.quest(new JresIndexDocument(index, type, new Document("yellow car")));
        jres.quest(new JresRefresh(index));
        JresSearchReply quest = jres.quest(new JresSearch(index, type));
        Assert.assertEquals(new Integer(3), quest.getHits().getTotal());

        JresSearchReply searchReply = jres.quest(new JresSearch(index, type,
                new JresSearchBody().query(
                        new JresDisMaxQuery(
                                new JresMatchQuery("description", "yellow")
                        )
                )
        ));
        Assert.assertEquals(new Integer(2), searchReply.getHits().getTotal());
        for (Document document : searchReply.getHitsAsType(Document.class)) {
            Assert.assertTrue(document.description.contains("yellow"));
        }
    }

    static class Document {
        public String description;

        Document() {
        }

        Document(String description) {
            this.description = description;
        }
    }
}
