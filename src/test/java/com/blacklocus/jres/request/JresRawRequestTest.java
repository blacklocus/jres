package com.blacklocus.jres.request;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.index.JresRefresh;
import com.blacklocus.jres.response.search.JresSearchReply;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpPost;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresRawRequestTest extends JresTest {

    @Test
    public void test() {
        String index = "JresRawRequestTest_test".toLowerCase();

        jres.quest(new JresIndexDocument(index, "test", new Document("big")));
        jres.quest(new JresIndexDocument(index, "test", new Document("small")));
        jres.quest(new JresRefresh(index));

        List<Document> hits = jres.quest(new JresRawRequest<JresSearchReply>(
                HttpPost.METHOD_NAME,
                JresPaths.slashed(index) + "_search",
                "{\"query\":{\"match_all\":{}}}",
                JresSearchReply.class
        )).getHitsAsType(Document.class);
        Assert.assertEquals(2, hits.size());

        hits = jres.quest(new JresRawRequest<JresSearchReply>(
                HttpPost.METHOD_NAME,
                JresPaths.slashed(index) + "_search",
                JresRawRequestTest.class.getResource("raw query.json"),
                JresSearchReply.class
        )).getHitsAsType(Document.class);
        Assert.assertEquals(1, hits.size());
    }

    static class Document {
        public String value = "yellow orange five seven";
        public String girth;

        Document(String girth) {
            this.girth = girth;
        }
    }
}
