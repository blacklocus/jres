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
package com.blacklocus.jres.response.common;

import com.blacklocus.jres.request.alias.JresAddAlias;
import com.blacklocus.jres.request.alias.JresDeleteAlias;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.request.index.JresGetIndexSettings;
import com.blacklocus.jres.request.mapping.JresGetMapping;
import com.blacklocus.jres.request.mapping.JresPutMapping;
import com.blacklocus.jres.response.JresReply;
import com.blacklocus.jres.strings.ObjectMappers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Possible response for <ul>
 * <li>{@link JresCreateIndex}</li>
 * <li>{@link JresGetIndexSettings}</li>
 * <li>{@link JresPutMapping}</li>
 * <li>{@link JresGetMapping}</li>
 * <li>{@link JresAddAlias}</li>
 * <li>{@link JresDeleteAlias}</li>
 * </ul>
 */
public class JresErrorReplyException extends RuntimeException implements JresReply {

    private final String error;
    private final Integer status;
    private final JsonNode node;

    public JresErrorReplyException(String error, Integer status, JsonNode node) {
        super(error);
        this.error = error;
        this.status = status;
        this.node = node;
    }

    @Override
    public JsonNode node() {
        return node;
    }

    public String getError() {
        return error;
    }

    public Integer getStatus() {
        return status;
    }

    public <T> T asType(Class<T> klass) {
        return ObjectMappers.fromJson(node, klass);
    }

    public <T> T asType(TypeReference<T> typeReference) {
        return ObjectMappers.fromJson(node, typeReference);
    }
}
