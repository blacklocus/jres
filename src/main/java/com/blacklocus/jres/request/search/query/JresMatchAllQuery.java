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
package com.blacklocus.jres.request.search.query;

import org.codehaus.jackson.annotate.JsonValue;

import java.util.Collections;
import java.util.Map;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresMatchAllQuery implements JresQuery {

    private final Map<Object, Object> value = Collections.emptyMap();

    @Override
    public String queryType() {
        return "match_all";
    }

    @JsonValue
    public Map<Object, Object> getValue() {
        return value;
    }
}
