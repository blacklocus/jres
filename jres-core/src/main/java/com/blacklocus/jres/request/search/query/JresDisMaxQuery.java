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
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JresDisMaxQuery implements JresQuery {

    /** Each map is a single entry from {@link JresQuery#queryType()} to the JresQuery itself */
    private final List<Map<String, JresQuery>> queries;

    public JresDisMaxQuery(JresQuery... queries) {
        this(Arrays.asList(queries));
    }

    public JresDisMaxQuery(List<JresQuery> queries) {
        this.queries = Lists.transform(queries, new Function<JresQuery, Map<String, JresQuery>>() {
            @Override
            public Map<String, JresQuery> apply(JresQuery input) {
                return ImmutableMap.of(input.queryType(), input);
            }
        });
    }

    @Override
    public String queryType() {
        return "dis_max";
    }

    @Override
    public String toString() {
        return ObjectMappers.toJson(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serializable getter properties

    public List<Map<String, JresQuery>> getQueries() {
        return queries;
    }
}
