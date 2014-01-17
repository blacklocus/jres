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
package com.blacklocus.jres.request.search;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.index.JresRefresh;
import com.blacklocus.jres.request.search.query.JresMatchAllQuery;
import com.blacklocus.jres.request.search.query.JresMatchQuery;
import com.blacklocus.jres.response.search.JresSearchReply;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresDeleteByQueryTest extends BaseJresTest {

    @Test
    public void test() {
        String index = "JresDeleteByQueryTest.test".toLowerCase();
        String type = "test";

        jres.quest(new JresIndexDocument(index, type, new Document()));
        jres.quest(new JresRefresh(index));
        JresSearchReply searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals((Object) 1L, searchReply.getHits().getTotal());

        jres.quest(new JresDeleteByQuery(index, type, new JresMatchQuery("bogus", "value")));
        jres.quest(new JresRefresh(index));
        searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals((Object) 1L, searchReply.getHits().getTotal());

        jres.quest(new JresDeleteByQuery(index, type, new JresMatchAllQuery()));
        jres.quest(new JresRefresh(index));
        searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals((Object) 0L, searchReply.getHits().getTotal());
    }

    static class Document {
        public String value = "wooters";
    }
}
