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

/**
 * ElasticSearch models most operations as actions, and many (all?) actions can often be chained into a bulk request.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
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

}
