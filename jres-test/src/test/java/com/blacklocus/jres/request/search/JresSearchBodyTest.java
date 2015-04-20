/**
 * Copyright 2015 BlackLocus
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
package com.blacklocus.jres.request.search;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.request.index.JresRefresh;
import com.blacklocus.jres.response.search.JresSearchReply;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.of;

public class JresSearchBodyTest extends BaseJresTest {

    @Test
    public void testFields() {
        String index = "JresSearchBodyTest.testFields".toLowerCase();
        String type = "test";
        jres.quest(new JresIndexDocument(index, type, of("character", "dino")));
        jres.quest(new JresRefresh(index));
        TypeReference<Map<String, String>> typeRef = new TypeReference<Map<String, String>>() {};

        JresSearchReply reply;
        List<Map<String, String>> results;
        Map<String, String> result;

        // no setting
        reply = jres.quest(new JresSearch(index, type, new JresSearchBody()));
        results = reply.getHitsAsType(typeRef);
        Assert.assertEquals(1, results.size());
        result = results.get(0);
        Assert.assertEquals("dino", result.get("character"));

        // no fields
        reply = jres.quest(new JresSearch(index, type, new JresSearchBody().fields()));
        results = reply.getHitsAsType(typeRef);
        Assert.assertEquals(1, results.size());
        result = results.get(0);
        Assert.assertNull(result);

        // non-default fields
        reply = jres.quest(new JresSearch(index, type, new JresSearchBody().fields("_timestamp")));
        results = reply.getHitsAsType(typeRef);
        Assert.assertEquals(1, results.size());
        result = results.get(0);
        Assert.assertNull(result);
    }

    @Test
    public void testQueryAsMap() {
        String index = "JresSearchBodyTest.testQueryAsMap".toLowerCase();
        String type = "test";
        jres.quest(new JresIndexDocument(index, type, of("character", "fred")));
        jres.quest(new JresIndexDocument(index, type, of("character", "wilma")));
        jres.quest(new JresRefresh(index));

        TypeReference<Map<String, String>> typeRef = new TypeReference<Map<String, String>>() {};
        JresSearchReply reply;
        List<Map<String, String>> results;
        Map<String, String> result;

        reply = jres.quest(new JresSearch(index, type, new JresSearchBody()));
        results = reply.getHitsAsType(typeRef);
        Assert.assertEquals(2, results.size());

        reply = jres.quest(new JresSearch(index, type, new JresSearchBody().query("match", of("character", "wilma"))));
        results = reply.getHitsAsType(typeRef);
        Assert.assertEquals(1, results.size());
        result = results.get(0);
        Assert.assertEquals("wilma", result.get("character"));
    }
}
