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
package com.blacklocus.jres.request.search.query;

import org.junit.Test;

public class JresQueryTest {

    @Test
    public void testSomeToStrings() {
        // Just make sure these toString's don't except. For humans to look at.
        System.out.println(new JresBoolQuery()
                .must(new JresQueryStringQuery("stuff:true").withDefaultOperator("AND"))
                .should(
                        new JresMatchQuery("color", "orange"),
                        new JresTermQuery("key", "abc123")
                )
                .mustNot(new JresDisMaxQuery(
                        new JresMatchQuery("color", "yellow"),
                        new JresMatchQuery("color", "green")
                ))
        );
    }
}
