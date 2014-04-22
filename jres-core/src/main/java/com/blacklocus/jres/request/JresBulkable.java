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
package com.blacklocus.jres.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * ElasticSearch models most operations as actions, and many (all?) actions can often be chained into a bulk request.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface JresBulkable {

    /**
     * @return bulk command
     */
    Object getAction();

    /**
     * @return (optional) payload of action, e.g. index operation is followed by the document to be indexed
     */
    Object getPayload();

    /**
     * @return target ElasticSearch index
     */
    String getIndex();

    /**
     * @return target ElasticSearch type
     */
    String getType();

    /*
     * @return target document id
     */
    String getId();

    /**
     * In jackson serialization, the darned @JsonTypeInfo annotation is not dynamically looked up based on runtime type,
     * but declared type. In the case where JresBulkables are type-erased (such as in collections) the declared type
     * becomes effectively Object which means no @JsonTypeInfo. The serialization includes no type information.
     * Jackson could have <strong>easily</strong> examined the runtime types for @JsonTypeInfo annotations, but they
     * just didn't. So subclasses must implement this 'special' property to be picked up on the other end for
     * proper polymorphic deserialization.
     * <p/>
     * Realize that this means Jackson deserialization has NO issues with finding the @JsonTypeInfo annotation on a
     * type-erased generic type. This is only a workaround for the shortcoming of the serialization side.
     *
     * @return the fully-qualified name of the sub-class
     */
    @JsonProperty("@class")
    String getJsonTypeInfo();
}
