/**
 * Copyright 2015 BlackLocus
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
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import org.apache.http.client.methods.HttpPost;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-update.html">Update Document API</a>
 *
 * <p>Covers the scripted update portion.
 *
 * <p><b>Note:</b> ElasticSearch defaults the script lang to groovy, but declares the groovy dependency as optional.
 * You must explicitly include groovy 2.4+ in the classpath. Also, you must explicitly enable dynamic scripting in
 * elasticsearch.yml: <code>script.disable_dynamic: false</code>
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class JresUpdateDocumentScript extends JresJsonRequest<JresIndexDocumentReply> implements JresBulkable {

    private @Nullable String index;
    private @Nullable String type;
    private String id;

    private String script;
    private @Nullable Map<String, ?> params;
    private @Nullable Object upsert;
    private Integer retryOnConflict;

    // for JSON deserialization
    JresUpdateDocumentScript() {
        super(JresIndexDocumentReply.class);
    }

    /**
     * `index` or `type` are nullable if this operation is to be included in a {@link JresBulk} request which specifies
     * a default index or type, respectively.
     *
     * @param script ElasticSearch update script
     */
    public JresUpdateDocumentScript(@Nullable String index, @Nullable String type, String id, String script) {
        this(index, type, id, script, null, null, null);
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
        this(index, type, id, script, params, null, null);
    }

    /**
     * `index` or `type` are nullable if this operation is to be included in a {@link JresBulk} request which specifies
     * a default index or type, respectively.
     *
     * @param script ElasticSearch update script
     * @param params (optional) corresponding to update script
     * @param upsert (optional) initial document to index if no such document exists at the given `id`
     * @param retryOnConflict (optional) how many times ElasticSearch should retry an update if concurrent writes are
     *                        causing version conflict exceptions
     */
    public JresUpdateDocumentScript(@Nullable String index, @Nullable String type, String id, String script,
                                    @Nullable Map<String, ?> params, @Nullable Object upsert, Integer retryOnConflict) {
        super(JresIndexDocumentReply.class);
        this.index = index;
        this.type = type;
        this.id = id;
        this.script = script;
        this.params = params;
        this.upsert = upsert;
        this.retryOnConflict = retryOnConflict;
    }

    @Override
    @Nullable
    @JsonProperty
    public String getIndex() {
        return index;
    }

    public void setIndex(@Nullable String index) {
        this.index = index;
    }

    @Override
    @Nullable
    @JsonProperty
    public String getType() {
        return type;
    }

    public void setType(@Nullable String type) {
        this.type = type;
    }

    @Override
    @JsonProperty
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty
    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Nullable
    @JsonProperty
    public Map<String, ?> getParams() {
        return params;
    }

    public void setParams(@Nullable Map<String, ?> params) {
        this.params = params;
    }

    @Nullable
    @JsonProperty
    public Object getUpsert() {
        return upsert;
    }

    public void setUpsert(@Nullable Object upsert) {
        this.upsert = upsert;
    }

    @JsonProperty
    public Integer getRetryOnConflict() {
        return retryOnConflict;
    }

    public void setRetryOnConflict(Integer retryOnConflict) {
        this.retryOnConflict = retryOnConflict;
    }

    @Override
    public String getJsonTypeInfo() {
        return JresUpdateDocumentScript.class.getName();
    }

    @Override
    public String getHttpMethod() {
        return HttpPost.METHOD_NAME;
    }

    @Override
    public String getPath() {
        String path = JresPaths.slashedPath(index, type, id) + "_update";
        if (retryOnConflict != null) {
            path += "?retry_on_conflict=" + retryOnConflict;
        }
        return path;

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
                "_id", id,
                "_retry_on_conflict", retryOnConflict
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
        if (retryOnConflict != null ? !retryOnConflict.equals(that.retryOnConflict) : that.retryOnConflict != null)
            return false;
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
        result = 31 * result + (retryOnConflict != null ? retryOnConflict.hashCode() : 0);
        return result;
    }
}
