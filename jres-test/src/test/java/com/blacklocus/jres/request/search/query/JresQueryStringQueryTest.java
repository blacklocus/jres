package com.blacklocus.jres.request.search.query;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.index.JresRefresh;
import com.blacklocus.jres.request.search.JresSearch;
import com.blacklocus.jres.request.search.JresSearchBody;
import org.junit.Assert;
import org.junit.Test;

public class JresQueryStringQueryTest extends BaseJresTest {

    @Test
    public void test() {
        String index = "JresQueryStringQueryTest.test".toLowerCase();
        String type = "test";

        jres.quest(new JresIndexDocument(index, type, new Document()));
        jres.quest(new JresRefresh(index));
        Assert.assertEquals(0, jres.quest(new JresSearch(index, type, new JresSearchBody().query(
                new JresQueryStringQuery("thing:no")
        ))).getHitsAsType(Document.class).size());
        Assert.assertEquals(1, jres.quest(new JresSearch(index, type, new JresSearchBody().query(
                new JresQueryStringQuery("thing:ye*")
        ))).getHitsAsType(Document.class).size());
    }

    private static class Document {
        public String thing = "yes";
    }
}
