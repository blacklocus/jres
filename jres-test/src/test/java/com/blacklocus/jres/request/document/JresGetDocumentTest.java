/**
 * Copyright 2013 BlackLocus
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
package com.blacklocus.jres.request.document;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.JresImpl;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.response.document.JresGetDocumentReply;
import com.blacklocus.jres.response.index.JresIndexDocumentReply;
import org.junit.Assert;
import org.junit.Test;

public class JresGetDocumentTest extends BaseJresTest {

    @Test
    public void test() {
        String index = "JresGetDocumentTest.test".toLowerCase();
        String type = "banana";

        JresIndexDocumentReply indexDocumentReply = jres.quest(new JresIndexDocument(index, type, new Document()));
        String generatedId = indexDocumentReply.getId();

        JresImpl.Tolerance<JresGetDocumentReply> tolerance = jres.tolerate(new JresGetDocument(index, type, "bogus"), 404);
        Assert.assertTrue(tolerance.isError());
        Assert.assertFalse(tolerance.getError().asType(JresGetDocumentReply.class).getExists());

        JresGetDocumentReply getDocumentReply = jres.quest(new JresGetDocument(index, type, generatedId));
        Assert.assertTrue(getDocumentReply.getExists());
        Assert.assertEquals(generatedId, getDocumentReply.getId());
    }

    private static class Document {
        public Integer length = 42;
    }
}
