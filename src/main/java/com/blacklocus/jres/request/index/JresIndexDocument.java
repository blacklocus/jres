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
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

import java.util.HashMap;
import java.util.Map;

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

    public JresIndexDocument(String index, String type, Object document) {
        this(index, type, null, document);
    }

    public JresIndexDocument(String index, String type, String id, Object document) {
        super(JresIndexDocumentReply.class);
        this.index = index;
        this.type = type;
        this.id = id;
        this.document = document;
    }

    @Override
    public String getHttpMethod() {
        return id == null ? HttpPost.METHOD_NAME : HttpPut.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(index) + JresPaths.slashed(type) + (id == null ? "" : id);
    }

    @Override
    public Object getAction() {
        return new IndexDocumentAction();
    }

    @Override
    public Object getPayload() {
        return document;
    }

    static class IndexDocumentAction {
        public Map<String, Object> index = new HashMap<String, Object>();
    }

}
