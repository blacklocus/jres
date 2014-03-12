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
package com.blacklocus.jres.model.search;

import com.blacklocus.jres.strings.ObjectMappers;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Objects;

public class Hit {

    @JsonProperty("_index")
    private String index;

    @JsonProperty("_type")
    private String type;

    @JsonProperty("_id")
    private String id;

    @JsonProperty("_score")
    private Double score;

    @JsonProperty("_source")
    private JsonNode source;


    public String getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Double getScore() {
        return score;
    }

    public JsonNode getSource() {
        return source;
    }

    /**
     * May return <code>null</code> if source was not available.
     */
    public <T> T getSourceAsType(Class<T> klass) {
        return source == null ? null : ObjectMappers.fromJson(source, klass);
    }

    /**
     * May return <code>null</code> if source was not available.
     */
    public <T> T getSourceAsType(TypeReference<T> typeReference) {
        return source == null ? null : ObjectMappers.fromJson(source, typeReference);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("index", index)
                .add("type", type)
                .add("id", id)
                .add("score", score)
                .add("source", ObjectMappers.toJsonPretty(source))
                .toString();
    }
}
