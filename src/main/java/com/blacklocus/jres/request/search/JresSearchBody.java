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

import com.blacklocus.jres.request.search.query.JresQuery;
import com.blacklocus.jres.strings.ObjectMappers;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.util.Map;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresSearchBody {

    /** Single entry from {@link JresQuery#queryType()} to the JresQuery itself. */
    private Map<String, Object> query;
    private Integer size;

    public JresSearchBody query(JresQuery query) {
        this.query = ImmutableMap.<String, Object>of(query.queryType(), query);
        return this;
    }

    public JresSearchBody size(Integer size) {
        this.size = size;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends JresQuery> T getQuery(T sample) {
        try {
            String json = ObjectMappers.NORMAL.writeValueAsString(getQuery().get(sample.queryType()));
            T jresQuery = ObjectMappers.NORMAL.readValue(json, (Class<T>) sample.getClass());
            assert sample.queryType().equals(jresQuery.queryType());
            // Swap it in
            getQuery().put(jresQuery.queryType(), jresQuery);
            return jresQuery;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serializable getter properties

    public Map<String, Object> getQuery() {
        return query;
    }

    public Integer getSize() {
        return size;
    }

}
