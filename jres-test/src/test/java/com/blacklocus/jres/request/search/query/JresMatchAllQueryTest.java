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
import com.blacklocus.jres.model.search.Hit;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.index.JresRefresh;
import com.blacklocus.jres.request.search.JresSearch;
import com.blacklocus.jres.request.search.JresSearchBody;
import com.blacklocus.jres.response.search.JresSearchReply;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresMatchAllQueryTest extends BaseJresTest {

    @Test
    public void test() {
        String index = "JresMatchAllQueryTest.test".toLowerCase();
        String type = "test";

        jres.quest(new JresIndexDocument(index, type, "1", new Document()));
        jres.quest(new JresIndexDocument(index, type, "2", new Document()));
        jres.quest(new JresIndexDocument(index, type, "3", new Document()));
        jres.quest(new JresRefresh(index));

        JresSearchReply searchReply = jres.quest(new JresSearch(index, type, new JresSearchBody().query(new JresMatchAllQuery())));
        Assert.assertEquals(new Integer(3), searchReply.getHits().getTotal());
        Set<String> ids = Sets.newHashSet("1", "2", "3");
        for (Hit hit : searchReply.getHits().getHits()) {
            ids.remove(hit.getId());
        }
        Assert.assertEquals("All records should have been returned", 0, ids.size());
    }

    static class Document {
        public Integer the_answer = 42;
    }
}
