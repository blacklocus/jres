package com.blacklocus.jres.request.bulk;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.JresBulkable;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.index.JresRefresh;
import com.blacklocus.jres.request.mapping.JresPutMapping;
import com.blacklocus.jres.request.search.JresSearch;
import com.blacklocus.jres.response.bulk.JresBulkReply;
import com.blacklocus.jres.response.search.JresSearchReply;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresBulkRequestTest extends JresTest {

    @Test
    public void testHappy() {
        String index = "JresBulkRequestTest_happy".toLowerCase();
        String type = "test";

        jres.quest(new JresCreateIndex(index));
        jres.quest(new JresPutMapping(index, type));

        JresBulkReply bulkResponse = jres.quest(new JresBulk(index, type, Arrays.<JresBulkable>asList(
                new JresIndexDocument(index, type, new Document("one")),
                new JresIndexDocument(index, type, new Document("two"))
        )));
        Assert.assertEquals(2, bulkResponse.getItems().size());

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
