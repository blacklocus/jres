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
        Assert.assertEquals(new Integer(1), searchReply.getHits().getTotal());

        jres.quest(new JresDeleteByQuery(index, type, new JresMatchQuery("bogus", "value")));
        jres.quest(new JresRefresh(index));
        searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals(new Integer(1), searchReply.getHits().getTotal());

        jres.quest(new JresDeleteByQuery(index, type, new JresMatchAllQuery()));
        jres.quest(new JresRefresh(index));
        searchReply = jres.quest(new JresSearch(index, type));
        Assert.assertEquals(new Integer(0), searchReply.getHits().getTotal());
    }

    static class Document {
        public String value = "wooters";
    }
}
