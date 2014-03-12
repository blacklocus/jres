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
package com.blacklocus.jres.response;

import com.blacklocus.jres.strings.ObjectMappers;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class JresJsonReply implements JresReply {

    private JsonNode node;

    @Override
    public JsonNode node() {
        return node;
    }

    public JresJsonReply node(JsonNode node) {
        this.node = node;
        return this;
    }

    @Override
    public String toString() {
        try {
            return ObjectMappers.PRETTY.writeValueAsString(node());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
