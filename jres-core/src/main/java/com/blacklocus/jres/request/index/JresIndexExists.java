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

import com.blacklocus.jres.handler.JresPredicates;
import com.blacklocus.jres.request.JresBooleanRequest;
import com.google.common.base.Predicate;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpHead;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-exists.html#indices-exists">Index Exists API</a>
 */
public class JresIndexExists extends JresBooleanRequest {

    private final String index;

    public JresIndexExists(String index) {
        this.index = index;
    }

    @Override
    public String getHttpMethod() {
        return HttpHead.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return index;
    }

    @Override
    public Object getPayload() {
        return null;
    }

    @Override
    public Predicate<HttpResponse> getPredicate() {
        return JresPredicates.STATUS_200;
    }
}
