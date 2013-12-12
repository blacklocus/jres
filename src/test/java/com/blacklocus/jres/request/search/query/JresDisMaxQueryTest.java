package com.blacklocus.jres.request.search.query;

import com.blacklocus.jres.JresTest;
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
public class JresDisMaxQueryTest extends JresTest {

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
