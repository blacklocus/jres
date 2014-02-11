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
import com.blacklocus.jres.request.bulk.JresBulk;
import com.blacklocus.jres.response.index.JresIndexDocumentReply;
import com.blacklocus.misc.NoNullsMap;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import org.apache.http.client.methods.HttpPost;

import javax.annotation.Nullable;

import static com.blacklocus.jres.strings.JresPaths.slashedPath;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-update.html">Update Document API</a>
 *
 * Covers the non-scripted update portion.
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class JresUpdateDocument extends JresJsonRequest<JresIndexDocumentReply> implements JresBulkable {

    private final @Nullable String index;
    private final @Nullable String type;

    private final String id;
    private final Object document;
    private final Boolean docAsUpsert;

    /**
     * Update a document. If it does not exist will create one (<code>doc_as_upsert:true</code>). Note in ElasticSearch
     * "update" means to modify an existing document (e.g. merge or overwrite particular fields). To overwrite an
     * existing document with some id is not an ElasticSearch "update" operation, but rather an "index"
     * ({@link JresIndexDocument}) operation.
     * <p/>
     * `index` or `type` are nullable if this operation is to be included in a {@link JresBulk} request which specifies
     * a default index or type, respectively.
     */
    public JresUpdateDocument(@Nullable String index, @Nullable String type, String id, Object document) {
        this(index, type, id, document, true);
    }

    /**
     * Update a document. If it does not exist will create one. Note in ElasticSearch "update" means to modify an
     * existing document (e.g. merge or overwrite particular fields). To overwrite an existing document with some id is
     * not an ElasticSearch "update" operation, but rather an "index" ({@link JresIndexDocument}) operation.
     * <p/>
     * `index` or `type` are nullable if this operation is to be included in a {@link JresBulk} request which specifies
     * a default index or type, respectively.
     *
     * @param docAsUpsert If false, prevents ElasticSearch from automatically creating a new document.
     */
    @JsonCreator
    public JresUpdateDocument(@JsonProperty("index") @Nullable String index,
                              @JsonProperty("type") @Nullable String type,
                              @JsonProperty("id") String id,
                              @JsonProperty("document") Object document,
                              @JsonProperty("docAsUpsert") Boolean docAsUpsert) {
        super(JresIndexDocumentReply.class);
        this.index = index;
        this.type = type;
        this.id = id;
        this.document = document;
        this.docAsUpsert = docAsUpsert;
    }

    @Nullable
    @JsonProperty
    public String getIndex() {
        return index;
    }

    @Nullable
    @JsonProperty
    public String getType() {
        return type;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public Object getDocument() {
        return document;
    }

    @JsonProperty
    public Boolean isDocAsUpsert() {
        return docAsUpsert;
    }

    @Override
    public String getJsonTypeInfo() {
        return JresUpdateDocument.class.getName();
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
    public Object getPayload() {
        return ImmutableMap.of(
                "doc", document,
                "doc_as_upsert", docAsUpsert
        );
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JresUpdateDocument that = (JresUpdateDocument) o;

        if (docAsUpsert != null ? !docAsUpsert.equals(that.docAsUpsert) : that.docAsUpsert != null) return false;
        if (document != null ? !document.equals(that.document) : that.document != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (index != null ? !index.equals(that.index) : that.index != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = index != null ? index.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (document != null ? document.hashCode() : 0);
        result = 31 * result + (docAsUpsert != null ? docAsUpsert.hashCode() : 0);
        return result;
    }
}
