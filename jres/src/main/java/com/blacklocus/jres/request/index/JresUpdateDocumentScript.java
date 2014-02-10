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
import com.blacklocus.jres.strings.JresPaths;
import com.blacklocus.misc.NoNullsMap;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import org.apache.http.client.methods.HttpPost;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-update.html">Update Document API</a>
 *
 * Covers the scripted update portion.
 */
public class JresUpdateDocumentScript extends JresJsonRequest<JresIndexDocumentReply> implements JresBulkable {

    private final @Nullable String index;
    private final @Nullable String type;
    private final String id;

    private final String script;
    private final @Nullable Map<String, ?> params;
    private final @Nullable Object upsert;

    /**
     * `index` or `type` are nullable if this operation is to be included in a {@link JresBulk} request which specifies
     * a default index or type, respectively.
     *
     * @param script ElasticSearch update script
     */
    public JresUpdateDocumentScript(@Nullable String index, @Nullable String type, String id, String script) {
        this(index, type, id, script, null, null);
    }

    /**
     * `index` or `type` are nullable if this operation is to be included in a {@link JresBulk} request which specifies
     * a default index or type, respectively.
     *
     * @param script ElasticSearch update script
     * @param params (optional) corresponding to update script
     */
    public JresUpdateDocumentScript(@Nullable String index, @Nullable String type, String id, String script,
                                    @Nullable Map<String, ?> params) {
        this(index, type, id, script, params, null);
    }

    /**
     * `index` or `type` are nullable if this operation is to be included in a {@link JresBulk} request which specifies
     * a default index or type, respectively.
     *
     * @param script ElasticSearch update script
     * @param params (optional) corresponding to update script
     * @param upsert (optional) initial document to index if no such document exists at the given `id`
     */
    @JsonCreator
    public JresUpdateDocumentScript(@JsonProperty("index") @Nullable String index,
                                    @JsonProperty("type") @Nullable String type,
                                    @JsonProperty("id") String id,
                                    @JsonProperty("script") String script,
                                    @JsonProperty("params") @Nullable Map<String, ?> params,
                                    @JsonProperty("upsert") @Nullable Object upsert) {
        super(JresIndexDocumentReply.class);
        this.index = index;
        this.type = type;
        this.id = id;
        this.script = script;
        this.params = params;
        this.upsert = upsert;
    }

    @Override
    @Nullable
    public String getIndex() {
        return index;
    }

    @Override
    @Nullable
    public String getType() {
        return type;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getScript() {
        return script;
    }

    @Nullable
    public Map<String, ?> getParams() {
        return params;
    }

    @Nullable
    public Object getUpsert() {
        return upsert;
    }

    @Override
    public String getHttpMethod() {
        return HttpPost.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashedPath(index, type, id) + "_update";
    }

    @Override
    public Object getPayload() {
        return NoNullsMap.of(
                "script", script,
                "params", params,
                "upsert", upsert
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

        JresUpdateDocumentScript that = (JresUpdateDocumentScript) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (index != null ? !index.equals(that.index) : that.index != null) return false;
        if (params != null ? !params.equals(that.params) : that.params != null) return false;
        if (script != null ? !script.equals(that.script) : that.script != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (upsert != null ? !upsert.equals(that.upsert) : that.upsert != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = index != null ? index.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (script != null ? script.hashCode() : 0);
        result = 31 * result + (params != null ? params.hashCode() : 0);
        result = 31 * result + (upsert != null ? upsert.hashCode() : 0);
        return result;
    }
}
