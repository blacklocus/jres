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
    public void testAllTerms() {
        String index = "JresTermsFacetTest.testAllTerms".toLowerCase();
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
        Assert.assertEquals("terms", termsFacet.getType());
        Assert.assertEquals(Arrays.asList(
                new TermsFacet.Term("three", 3L),
                new TermsFacet.Term("two", 2L),
                new TermsFacet.Term("one", 1L)
        ), termsFacet.getTerms());

        System.out.println();
    }
}
