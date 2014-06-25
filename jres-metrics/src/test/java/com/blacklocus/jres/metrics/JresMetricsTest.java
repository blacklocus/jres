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
package com.blacklocus.jres.metrics;

import com.blacklocus.jres.Jres;
import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.request.JresRequest;
import com.blacklocus.jres.request.document.JresGetDocument;
import com.blacklocus.jres.request.index.JresIndexDocument;
import com.blacklocus.jres.response.document.JresGetDocumentReply;
import com.blacklocus.jres.response.index.JresIndexDocumentReply;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

public class JresMetricsTest {

    @Test
    public void testDecorate() {
        MetricRegistry registry = new MetricRegistry();
        Jres jres = Mockito.mock(Jres.class);
        final JresGetDocumentReply fakeGet = new JresGetDocumentReply();
        final long sleepMs = TimeUnit.SECONDS.toMillis(5);

        Mockito.when(jres.quest(Matchers.any(JresGetDocument.class))).thenAnswer(new Answer<JresGetDocumentReply>() {
            @Override
            public JresGetDocumentReply answer(InvocationOnMock invocation) throws Throwable {
                Thread.sleep(sleepMs); // 5 seconds, significant delay
                return fakeGet;
            }
        });

        Jres jresWithMetrics = new JresMetrics().withRegistry(registry).withJres(jres).build();

        long startMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
        JresGetDocumentReply reply = jresWithMetrics.quest(new JresGetDocument("someindex", "sometype", "someid"));
        long endMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());

        Assert.assertSame("Making sure we went through the mocked Answer.", fakeGet, reply);
        Assert.assertTrue("Coded a 5 second sleep. Even considering a thread can wake somewhat early, we still " +
                "should have delayed a few seconds.", endMs - startMs > sleepMs / 2); // delayed at least half the sleep time

        // There's only one timer
        Timer timer = registry.getTimers().values().iterator().next();
        Assert.assertEquals(1, timer.getCount());
        Assert.assertTrue(timer.getMeanRate() > 0);
    }

    @Test
    public void testCustomMetricNameFn() {
        MetricRegistry registry = new MetricRegistry();
        Jres jres = Mockito.mock(Jres.class);
        final JresGetDocumentReply fakeGet = new JresGetDocumentReply();
        final JresIndexDocumentReply fakeIndex = new JresIndexDocumentReply();
        final long sleepMs = TimeUnit.SECONDS.toMillis(3);

        Mockito.when(jres.quest(Matchers.any(JresJsonRequest.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Thread.sleep(sleepMs);
                Object arg0 = invocation.getArguments()[0];
                if (arg0 instanceof JresGetDocument) {
                    return fakeGet;
                } else if (arg0 instanceof JresIndexDocument) {
                    return fakeIndex;
                } else {
                    throw new RuntimeException("test setup failure");
                }
            }
        });

        Jres jresWithMetrics = new JresMetrics()
                .withRegistry(registry)
                .withJres(jres)
                .withMetricNameFn(new Function<JresRequest<?>, String>() {
                    @Override
                    public String apply(JresRequest<?> req) {
                        return req.getClass().getSimpleName();
                    }
                })
                .build();

        JresGetDocumentReply getReply = jresWithMetrics.quest(new JresGetDocument("someindex", "sometype", "someid"));
        Assert.assertSame(fakeGet, getReply);
        JresIndexDocumentReply indexReply = jresWithMetrics.quest(new JresIndexDocument("someindex", "sometype", "someid",
                ImmutableMap.of("field", "value")));
        Assert.assertSame(fakeIndex, indexReply);

        SortedMap<String, Timer> timers = registry.getTimers();
        Assert.assertEquals(2, timers.size());
        Assert.assertEquals(Arrays.asList("JresGetDocument", "JresIndexDocument"), new ArrayList<String>(timers.keySet()));
    }
}
