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
package com.blacklocus.jres.request.alias;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.alias.JresRetrieveAliasesReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpGet;

/**
 * <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-aliases.html#alias-adding">Add Single Index Alias API</a>
 * <p>
 * Can throw {@link JresErrorReplyException}.
 */
public class JresRetrieveAliases extends JresJsonRequest<JresRetrieveAliasesReply> {

    private final String indexPattern;
    private final String aliasPattern;

    /**
     * @param indexPattern which appears to accept any number of '*' wildcards interspersed with characters and multiple
     *                     expressions separated by ','
     * @param aliasPattern which appears to accept any number of '*' wildcards interspersed with characters and multiple
     *                     expressions separated by ','
     */
    public JresRetrieveAliases(String indexPattern, String aliasPattern) {
        super(JresRetrieveAliasesReply.class);
        this.indexPattern = indexPattern;
        this.aliasPattern = aliasPattern;
    }

    @Override
    public String getHttpMethod() {
        return HttpGet.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashedPath(indexPattern) + "_alias/" + aliasPattern;
    }

    @Override
    public Object getPayload() {
        return null;
    }

}
