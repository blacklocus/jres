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
package com.blacklocus.jres.request.search.facet;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.model.search.TermsFacet;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.index.JresRefresh;
import com.blacklocus.jres.request.mapping.JresPutMapping;
import com.blacklocus.jres.request.search.JresSearch;
import com.blacklocus.jres.request.search.JresSearchBody;
import com.blacklocus.jres.response.search.JresSearchReply;
import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class JresTermsFacetTest extends BaseJresTest {

    @Test
    public void testTerms() {
        String index = "JresTermsFacetTest.testTerms".toLowerCase();
        String type = "test";

        jres.quest(new JresCreateIndex(index));
        jres.quest(new JresPutMapping(index, type));
        jres.quest(new JresIndexDocument(index, type, ImmutableMap.of("value", "one")));
        jres.quest(new JresIndexDocument(index, type, ImmutableMap.of("value", "two")));
        jres.quest(new JresIndexDocument(index, type, ImmutableMap.of("value", "two")));
        jres.quest(new JresIndexDocument(index, type, ImmutableMap.of("value", "three")));
        jres.quest(new JresIndexDocument(index, type, ImmutableMap.of("value", "three")));
        jres.quest(new JresIndexDocument(index, type, ImmutableMap.of("value", "three")));
        jres.quest(new JresRefresh(index));

        JresSearchBody search = new JresSearchBody().size(0).facets(new JresTermsFacet("the_terms", "value"));
        JresSearchReply reply = jres.quest(new JresSearch(index, type, search));

        TermsFacet termsFacet = reply.getFacets().get("the_terms", TermsFacet.class);
        Assert.assertEquals((Object) 6L, termsFacet.getTotal());
        Assert.assertEquals((Object) 0L, termsFacet.getOther());
        Assert.assertEquals("terms", termsFacet.getType());
        Assert.assertEquals(Arrays.asList(
                new TermsFacet.Term("three", 3L),
                new TermsFacet.Term("two", 2L),
                new TermsFacet.Term("one", 1L)
        ), termsFacet.getTerms());
    }

    @Test
    public void testRegex() {
        String index = "JresTermsFacetTest.testRegex".toLowerCase();
        String type = "test";

        jres.quest(new JresCreateIndex(index));
        jres.quest(new JresPutMapping(index, type));
        jres.quest(new JresIndexDocument(index, type, ImmutableMap.of("value", "one")));
        jres.quest(new JresIndexDocument(index, type, ImmutableMap.of("value", "two")));
        jres.quest(new JresIndexDocument(index, type, ImmutableMap.of("value", "two")));
        jres.quest(new JresIndexDocument(index, type, ImmutableMap.of("value", "three")));
        jres.quest(new JresIndexDocument(index, type, ImmutableMap.of("value", "three")));
        jres.quest(new JresIndexDocument(index, type, ImmutableMap.of("value", "three")));
        jres.quest(new JresRefresh(index));

        JresSearchBody search = new JresSearchBody().size(0).facets(new JresTermsFacet("the_terms", "value").regex("t.*"));
        JresSearchReply reply = jres.quest(new JresSearch(index, type, search));

        TermsFacet termsFacet = reply.getFacets().get("the_terms", TermsFacet.class);
        Assert.assertEquals((Object) 6L, termsFacet.getTotal());
        Assert.assertEquals((Object) 1L, termsFacet.getOther());
        Assert.assertEquals("terms", termsFacet.getType());
        Assert.assertEquals(Arrays.asList(
                new TermsFacet.Term("three", 3L),
                new TermsFacet.Term("two", 2L)
                // one didn't match the regex "t.*"
        ), termsFacet.getTerms());
    }
}
