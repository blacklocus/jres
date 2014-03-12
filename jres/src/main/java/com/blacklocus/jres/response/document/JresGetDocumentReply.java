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
package com.blacklocus.jres.response.document;

import com.blacklocus.jres.response.JresJsonReply;
import com.blacklocus.jres.strings.ObjectMappers;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

public class JresGetDocumentReply extends JresJsonReply {

    private Boolean ok;

    @JsonProperty("_index")
    private String index;

    @JsonProperty("_type")
    private String type;

    @JsonProperty("_id")
    private String id;

    @JsonProperty("_version")
    private Integer version;

    private Boolean exists;

    @JsonProperty("_source")
    private JsonNode source;

    public Boolean getOk() {
        return ok;
    }

    public String getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public Boolean getExists() {
        return exists;
    }

    public JsonNode getSource() {
        return source;
    }

    public <T> T getSourceAsType(Class<T> klass) {
        return ObjectMappers.fromJson(source, klass);
    }

    public <T> T getSourceAsType(TypeReference<T> typeReference) {
        return ObjectMappers.fromJson(source, typeReference);
    }
}
