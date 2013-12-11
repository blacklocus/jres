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

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

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
                // Allow empty JSON objects where they might occur.
                .configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false)
                // Not all properties need to be mapped back into POJOs.
                .configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
