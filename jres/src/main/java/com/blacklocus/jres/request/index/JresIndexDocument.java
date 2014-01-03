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
import org.apache.http.client.methods.HttpPut;

import static com.blacklocus.jres.strings.JresPaths.slashed;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-index_.html#docs-index_">Index Document API</a>
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresIndexDocument extends JresJsonRequest<JresIndexDocumentReply> implements JresBulkable {

    private final String index;
    private final String type;

    private final String id;
    private final Object document;

    private final Boolean createOnly;

    /**
     * Index a document with no specified id. ElasticSearch will generate one.
     */
    public JresIndexDocument(String index, String type, Object document) {
        this(index, type, null, document);
    }

    /**
     * Index a document with a specified id. ElasticSearch will replace any existing document with this id.
     */
    public JresIndexDocument(String index, String type, String id, Object document) {
        this(index, type, id, document, false);
    }

    /**
     * Index a document with the specified id. If <code>`create`</code> ElasticSearch will error on an attempt to
     * update an existing document at the given id.
     */
    public JresIndexDocument(String index, String type, String id, Object document, boolean createOnly) {
        super(JresIndexDocumentReply.class);
        this.index = index;
        this.type = type;
        this.id = id;
        this.document = document;
        this.createOnly = createOnly;
    }

    @Override
    public String getHttpMethod() {
        return id == null ? HttpPost.METHOD_NAME : HttpPut.METHOD_NAME;
    }

    @Override
    public String getPath() {
        String path = slashed(index, type) + (id == null ? "" : id);
        if (createOnly) {
            path = slashed(path) + "?op_type=create";
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

}
