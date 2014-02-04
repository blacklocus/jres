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
package com.blacklocus.jres.response.search;

import com.blacklocus.jres.model.Shards;
import com.blacklocus.jres.model.search.Facets;
import com.blacklocus.jres.model.search.Hit;
import com.blacklocus.jres.model.search.Hits;
import com.blacklocus.jres.response.JresJsonReply;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresSearchReply extends JresJsonReply {

    private Integer took;

    @JsonProperty("timed_out")
    private Boolean timedOut;

    @JsonProperty("_shards")
    private Shards shards;

    private Hits hits;

    private Facets facets;


    public Integer getTook() {
        return took;
    }

    public Boolean isTimedOut() {
        return timedOut;
    }

    public Shards getShards() {
        return shards;
    }

    public Hits getHits() {
        return hits;
    }

    public Facets getFacets() {
        return facets;
    }

    /**
     * Shortcut to {@link Hit#getSourceAsType(Class)}
     */
    public <T> List<T> getHitsAsType(final Class<T> klass) {
        return Lists.transform(getHits().getHits(), new Function<Hit, T>() {
            @Override
            public T apply(Hit hit) {
                return hit.getSourceAsType(klass);
            }
        });
    }

    /**
     * Shortcut to {@link Hit#getSourceAsType(TypeReference)}
     */
    public <T> List<T> getHitsAsType(final TypeReference<T> typeReference) {
        return Lists.transform(getHits().getHits(), new Function<Hit, T>() {
            @Override
            public T apply(Hit hit) {
                return hit.getSourceAsType(typeReference);
            }
        });
    }
}
