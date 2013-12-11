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
