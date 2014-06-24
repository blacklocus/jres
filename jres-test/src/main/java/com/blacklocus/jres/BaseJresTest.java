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
package com.blacklocus.jres;

import com.google.common.base.Suppliers;
import org.junit.BeforeClass;

/**
 * Extend this to be guaranteed that a test ElasticSearch instance will be available for usage with the member
 * {@link #jres}.
 */
public class BaseJresTest {

    @BeforeClass
    public static void startLocalElasticSearch() {
        ElasticSearchTestInstance.triggerStaticInit();
    }

    /**
     * Configured to connect to a local ElasticSearch instance created specifically for unit testing
     */
    protected JresImpl jres = new JresImpl(Suppliers.ofInstance("http://localhost:9201"));

}
