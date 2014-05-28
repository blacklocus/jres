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

import com.blacklocus.misc.NoNullsMap;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

public class JresRangeQuery implements JresQuery {

    String field;
    Object gte;
    Object gt;
    Object lte;
    Object lt;
    Double boost;

    public JresRangeQuery() {
    }

    public JresRangeQuery(String field) {
        this.field = field;
    }

    public JresRangeQuery gte(Object value) {
        this.gte = value;
        return this;
    }

    public JresRangeQuery gt(Object value) {
        this.gt = value;
        return this;
    }

    public JresRangeQuery lte(Object value) {
        this.lte = value;
        return this;
    }

    public JresRangeQuery lt(Object value) {
        this.lt = value;
        return this;
    }

    @Override
    public String queryType() {
        return "range";
    }

    @JsonValue
    public Object getJsonValue() {
        return ImmutableMap.of(
                field, NoNullsMap.of(
                        "gte", gte,
                        "gt", gt,
                        "lte", lte,
                        "lt", lt
                )
        );
    }
}
