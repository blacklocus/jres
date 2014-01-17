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
package com.blacklocus.jres.request.search;

import com.blacklocus.jres.request.search.facet.JresFacet;
import com.blacklocus.jres.request.search.query.JresQuery;
import com.blacklocus.jres.strings.ObjectMappers;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresSearchBody {

    /** Single entry from {@link JresQuery#queryType()} to the JresQuery itself. */
    private Map<String, Object> query;
    private Map<String, Object> facets;
    private Integer size;

    public JresSearchBody query(JresQuery query) {
        this.query = ImmutableMap.<String, Object>of(query.queryType(), query);
        return this;
    }

    public JresSearchBody facets(JresFacet... facets) {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        for (JresFacet facet : facets) {
            builder.put(facet.facetName(), ImmutableMap.of(facet.facetType(), facet));
        }
        this.facets = builder.build();
        return this;
    }

    public JresSearchBody size(Integer size) {
        this.size = size;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends JresQuery> T getQuery(T sample) {
        String json = ObjectMappers.toJson(getQuery().get(sample.queryType()));
        T jresQuery = ObjectMappers.fromJson(json, (Class<T>) sample.getClass());
        assert sample.queryType().equals(jresQuery.queryType());
        // Swap it in
        getQuery().put(jresQuery.queryType(), jresQuery);
        return jresQuery;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serializable getter properties

    public Map<String, Object> getQuery() {
        return query;
    }

    public Map<String, Object> getFacets() {
        return facets;
    }

    public Integer getSize() {
        return size;
    }

}
