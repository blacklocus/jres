package com.blacklocus.jres.request.index;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.response.index.JresIndexDocumentReply;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresIndexDocumentTest extends JresTest {

    @Test
    public void test() {
        String index = "JresIndexDocumentTest_test".toLowerCase();
        String type = "test";

        Document document = new Document();
        JresIndexDocumentReply reply = jres.quest(new JresIndexDocument(index, type, document));

        Assert.assertTrue(reply.isOk());
        Assert.assertEquals(reply.getIndex(), index);
        Assert.assertEquals(reply.getType(), type);
        Assert.assertEquals(reply.getVersion(), "1");
    }

    static class Document {
        public String value = "wheee";
    }
}
