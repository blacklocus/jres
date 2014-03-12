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

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.junit.Test;

public class JresGetIndexSettingsTest extends BaseJresTest {

    @Test
    public void testHappy() {
        String indexName = "JresGetIndexSettingsRequestTest_testHappy".toLowerCase();

        jres.quest(new JresCreateIndex(indexName));
        jres.quest(new JresGetIndexSettings(indexName));
    }

    @Test(expected = JresErrorReplyException.class)
    public void testSad() {
        String indexName = "JresGetIndexSettingsRequestTest_testSad".toLowerCase();

        jres.quest(new JresGetIndexSettings(indexName));
    }

}
