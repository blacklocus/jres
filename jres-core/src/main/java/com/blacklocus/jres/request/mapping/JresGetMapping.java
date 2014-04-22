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
package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.JresJsonReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpGet;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-get-mapping.html#indices-get-mapping">Get Mapping API</a>
 * <p>
 * Can throw {@link JresErrorReplyException}
 */
public class JresGetMapping extends JresJsonRequest<JresJsonReply> {

    private final String index;
    private final String type;

    public JresGetMapping(String index, String type) {
        super(JresJsonReply.class);
        this.index = index;
        this.type = type;
    }

    @Override
    public String getHttpMethod() {
        return HttpGet.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashedPath(index) + JresPaths.slashedPath(type) + "_mapping";
    }

    @Override
    public Object getPayload() {
        return null;
    }

}
