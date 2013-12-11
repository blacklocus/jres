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

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.index.JresRefresh;
import com.blacklocus.jres.request.mapping.JresPutMapping;
import com.blacklocus.jres.response.search.JresSearchReply;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresSearchTest extends JresTest {

    @Test
    public void test() throws InterruptedException {
        String index = "JresSearchRequestTest_test".toLowerCase();
        String type = "test";

        jres.quest(new JresCreateIndex(index));
        jres.quest(new JresPutMapping(index, type));
        jres.quest(new JresIndexDocument(index, type, new Document("one")));
        jres.quest(new JresIndexDocument(index, type, new Document("two")));
        jres.quest(new JresRefresh(index));

        JresSearchReply searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals(new Integer(2), searchReply.getHits().getTotal());
    }

    static class Document {
        public String value;

        Document(String value) {
            this.value = value;
        }
    }
}
