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
