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
package com.blacklocus.jres.strings;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class ObjectMappers {

    public static final ObjectMapper NORMAL = newConfiguredObjectMapper();

    public static final ObjectMapper PRETTY = newConfiguredObjectMapper();
    static {
        PRETTY.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
    }

    static ObjectMapper newConfiguredObjectMapper() {
        return new ObjectMapper()
                // ElasticSearch is usually more okay with absence of properties rather than presence with null values.
                .setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL)
                // Allow empty JSON objects where they might occur.
                .configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false)
                // Not all properties need to be mapped back into POJOs.
                .configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Convenience around {@link ObjectMapper#writeValueAsString(Object)} with {@link ObjectMappers#NORMAL} wrapping
     * checked exceptions in {@link RuntimeException}
     */
    public static String toJson(Object o) {
        try {
            return NORMAL.writeValueAsString(o);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convenience around {@link ObjectMapper#readValue(String, Class)} with {@link ObjectMappers#NORMAL} wrapping
     * checked exceptions in {@link RuntimeException}
     */
    public static <T> T fromJson(String json, Class<T> klass) {
        try {
            return NORMAL.readValue(json, klass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convenience around {@link ObjectMapper#readValue(InputStream, Class)} with {@link ObjectMappers#NORMAL} wrapping
     * checked exceptions in {@link RuntimeException}
     */
    public static <T> T fromJson(InputStream json, Class<T> klass) {
        try {
            return NORMAL.readValue(json, klass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convenience around {@link ObjectMapper#readValue(InputStream, TypeReference)} with {@link ObjectMappers#NORMAL} wrapping
     * checked exceptions in {@link RuntimeException}
     */
    public static <T> T fromJson(InputStream json, TypeReference<T> typeReference) {
        try {
            return NORMAL.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convenience around {@link ObjectMapper#readValue(JsonNode, Class)} with {@link ObjectMappers#NORMAL} wrapping
     * checked exceptions in {@link RuntimeException}
     */
    public static <T> T fromJson(JsonNode json, Class<T> klass) {
        try {
            return NORMAL.readValue(json, klass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convenience around {@link ObjectMapper#readValue(JsonNode, TypeReference)} with {@link ObjectMappers#NORMAL} wrapping
     * checked exceptions in {@link RuntimeException}
     */
    public static <T> T fromJson(JsonNode json, TypeReference<T> typeReference) {
        try {
            return NORMAL.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
