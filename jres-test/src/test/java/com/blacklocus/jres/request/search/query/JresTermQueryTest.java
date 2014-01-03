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
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresTermQueryTest extends BaseJresTest {

    @Test
    public void test() {
        String index = "JresTermQueryTest.test".toLowerCase();
        String type = "test";

        jres.quest(new JresIndexDocument(index, type, new Document()));
        jres.quest(new JresRefresh(index));
        Assert.assertEquals(0, jres.quest(new JresSearch(index, type, new JresSearchBody().query(
                new JresTermQuery("thing", "ye")
        ))).getHitsAsType(Document.class).size());
        Assert.assertEquals(1, jres.quest(new JresSearch(index, type, new JresSearchBody().query(
                new JresTermQuery("thing", "yes")
        ))).getHitsAsType(Document.class).size());
    }

    private static class Document {
        public String thing = "yes";
    }
}
