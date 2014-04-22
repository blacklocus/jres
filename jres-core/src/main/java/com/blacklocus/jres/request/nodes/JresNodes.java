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
package com.blacklocus.jres.request.nodes;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.JresJsonReply;
import org.apache.http.client.methods.HttpGet;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/cluster-nodes-info.html#cluster-nodes-info">Nodes API</a>
 */
public class JresNodes extends JresJsonRequest<JresJsonReply> {

    public JresNodes() {
        super(JresJsonReply.class);
    }

    @Override
    public String getHttpMethod() {
        return HttpGet.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return "_nodes";
    }

    @Override
    public Object getPayload() {
        return null;
    }

}
