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
package com.blacklocus.jres.request.index;

import com.blacklocus.jres.request.JresBulkable;
import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.index.JresIndexDocumentReply;
import com.blacklocus.misc.NoNullsMap;
import com.google.common.collect.ImmutableMap;
import org.apache.http.client.methods.HttpPost;

import static com.blacklocus.jres.strings.JresPaths.slashedPath;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-update.html">Update Document API</a>
 */
public class JresUpdateDocument extends JresJsonRequest<JresIndexDocumentReply> implements JresBulkable {

    private final String index;
    private final String type;

    private final String id;
    private final Object document;

    private final boolean docAsUpsert;

    /**
     * Update a document. If it does not exist will create one (<code>doc_as_upsert:true</code>). Note in ElasticSearch
     * "update" means to modify an existing document (e.g. merge or overwrite particular fields). To overwrite an
     * existing document with some id is not an ElasticSearch "update" operation, but rather an "index"
     * ({@link JresIndexDocument}) operation.
     */
    public JresUpdateDocument(String index, String type, String id, Object document) {
        this(index, type, id, document, true);
    }

    /**
     * Update a document. If it does not exist will create one. Note in ElasticSearch "update" means to modify an
     * existing document (e.g. merge or overwrite particular fields). To overwrite an existing document with some id is
     * not an ElasticSearch "update" operation, but rather an "index" ({@link JresIndexDocument}) operation.
     *
     * @param docAsUpsert If false, prevents ElasticSearch from automatically creating a new document.
     */
    public JresUpdateDocument(String index, String type, String id, Object document, boolean docAsUpsert) {
        super(JresIndexDocumentReply.class);
        this.index = index;
        this.type = type;
        this.id = id;
        this.document = document;
        this.docAsUpsert = docAsUpsert;
    }

    @Override
    public String getHttpMethod() {
        return HttpPost.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return slashedPath(index, type, id) + "_update";
    }

    @Override
    public Object getAction() {
        return ImmutableMap.of("update", NoNullsMap.of(
                "_index", index,
                "_type", type,
                "_id", id
        ));
    }

    @Override
    public Object getPayload() {
        return ImmutableMap.of(
                "doc", document,
                "doc_as_upsert", docAsUpsert
        );
    }
}
