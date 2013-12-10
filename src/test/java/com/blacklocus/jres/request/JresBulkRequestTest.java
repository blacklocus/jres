package com.blacklocus.jres.request;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.bulk.JresBulk;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.mapping.JresPutMapping;
import com.blacklocus.jres.request.search.JresSearch;
import com.blacklocus.jres.response.bulk.JresBulkReply;
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

        Document document = new Document();
        JresBulkReply bulkResponse = jres.quest(new JresBulk(index, type, Arrays.<JresBulkable>asList(
                new JresIndexDocument(index, type, document))));

        bulkResponse = jres.quest(new JresBulk(index, type, Arrays.<JresBulkable>asList(
                new JresIndexDocument(index, type, document))));

        jres.quest(new JresSearch(index, type));
//        searchResponse
    }

    @Test
    public void testMelancholy() {
        String index = "JresBulkRequestTest_melancholy".toLowerCase();
        String type = "test";

        jres.quest(new JresCreateIndex(index));
        jres.quest(new JresPutMapping(index, type));
    }

    static class Document {
        public String value = "yes";
    }
}
