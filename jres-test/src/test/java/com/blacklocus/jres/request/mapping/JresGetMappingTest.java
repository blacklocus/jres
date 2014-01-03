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
package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresGetMappingTest extends BaseJresTest {

    @Test(expected = JresErrorReplyException.class)
    public void sad() {
        String index = "JresGetMappingRequestTest_sad".toLowerCase();
        String type = "test";

        jres.quest(new JresGetMapping(index, type));
    }

    @Test
    public void happy() {
        String index = "JresGetMappingRequestTest_happy".toLowerCase();
        String type = "test";

        jres.quest(new JresCreateIndex(index));
        jres.quest(new JresPutMapping(index, type));

        jres.quest(new JresGetMapping(index, type));
    }
}
