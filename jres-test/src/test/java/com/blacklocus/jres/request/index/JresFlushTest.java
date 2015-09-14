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
package com.blacklocus.jres.request.index;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.response.common.JresShardsReply;
import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;

public class JresFlushTest extends BaseJresTest {

    @Test
    public void test() {
        String index = "JresFlushTest_test".toLowerCase();

        jres.quest(new JresCreateIndex(index));
        JresShardsReply flushReply = jres.quest(new JresFlush(index));
        Assert.assertEquals("" + flushReply.node(), 0, (int) flushReply.getShards().getFailed());
    }
}
