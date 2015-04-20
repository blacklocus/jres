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

import com.blacklocus.jres.strings.ObjectMappers;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Collections;
import java.util.Map;

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

    @Override
    public String toString() {
        return ObjectMappers.toJson(this);
    }

}
