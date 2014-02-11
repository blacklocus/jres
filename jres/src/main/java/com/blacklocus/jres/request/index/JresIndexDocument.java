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
import org.apache.http.client.methods.HttpPut;

import javax.annotation.Nullable;

import static com.blacklocus.jres.strings.JresPaths.slashedPath;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-index_.html#docs-index_">Index Document API</a>
 *
 * @author Jason Dunkelberger (dirkraft)
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class JresIndexDocument extends JresJsonRequest<JresIndexDocumentReply> implements JresBulkable {

    private final @Nullable String index;
    private final @Nullable String type;

    private final @Nullable String id;
    private final Object document;

    private final Boolean createOnly;

    /**
     * Index a document with no specified id. ElasticSearch will generate one. `index` or `type` are nullable
     * if this operation is to be included in a {@link JresBulk} request which specifies a default index or type,
     * respectively.
     */
    public JresIndexDocument(@Nullable String index, @Nullable String type, Object document) {
        this(index, type, null, document);
    }

    /**
     * Index a document with a specified id. ElasticSearch will replace any existing document with this id.
     * `index` or `type` are nullable if this operation is to be included in a {@link JresBulk} request which specifies
     * a default index or type, respectively.
     */
    public JresIndexDocument(@Nullable String index, @Nullable String type, @Nullable String id, Object document) {
        this(index, type, id, document, false);
    }

    /**
     * Index a document with the specified id. If <code>`create`</code> ElasticSearch will error on an attempt to
     * update an existing document at the given id. `index` or `type` are nullable if this operation is to be included
     * in a {@link JresBulk} request which specifies a default index or type, respectively.
     */
    @JsonCreator
    public JresIndexDocument(@JsonProperty("index") @Nullable String index,
                             @JsonProperty("type") @Nullable String type,
                             @JsonProperty("id") @Nullable String id,
                             @JsonProperty("document") Object document,
                             @JsonProperty("createOnly") boolean createOnly) {
        super(JresIndexDocumentReply.class);
        this.index = index;
        this.type = type;
        this.id = id;
        this.document = document;
        this.createOnly = createOnly;
    }

    @Override
    @Nullable
    @JsonProperty
    public String getIndex() {
        return index;
    }

    @Override
    @Nullable
    @JsonProperty
    public String getType() {
        return type;
    }

    @Override
    @Nullable
    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public Object getDocument() {
        return document;
    }

    @JsonProperty
    public Boolean getCreateOnly() {
        return createOnly;
    }

    @Override
    public String getJsonTypeInfo() {
        return JresIndexDocument.class.getName();
    }

    @Override
    public String getHttpMethod() {
        return id == null ? HttpPost.METHOD_NAME : HttpPut.METHOD_NAME;
    }

    @Override
    public String getPath() {
        String path = slashedPath(index, type) + (id == null ? "" : id);
        if (createOnly) {
            path = slashedPath(path) + "?op_type=create";
        }
        return path;
    }

    @Override
    public Object getAction() {
        return ImmutableMap.of("index", NoNullsMap.of(
                "_index", index,
                "_type", type,
                "_id", id,
                "op_type", createOnly ? "create" : null
        ));
    }

    @Override
    public Object getPayload() {
        return document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JresIndexDocument that = (JresIndexDocument) o;

        if (createOnly != null ? !createOnly.equals(that.createOnly) : that.createOnly != null) return false;
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
        result = 31 * result + (createOnly != null ? createOnly.hashCode() : 0);
        return result;
    }
}
