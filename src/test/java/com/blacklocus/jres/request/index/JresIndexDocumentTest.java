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

        Assert.assertTrue(reply.getOk());
        Assert.assertEquals(reply.getIndex(), index);
        Assert.assertEquals(reply.getType(), type);
        Assert.assertEquals(reply.getVersion(), "1");
    }

    static class Document {
        public String value = "wheee";
    }
}
