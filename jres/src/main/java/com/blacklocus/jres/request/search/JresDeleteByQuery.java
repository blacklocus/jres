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
package com.blacklocus.jres.request.search;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.request.search.query.JresQuery;
import com.blacklocus.jres.response.common.JresIndicesReply;
import com.blacklocus.jres.strings.JresPaths;
import com.google.common.collect.ImmutableMap;
import org.apache.http.client.methods.HttpDelete;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresDeleteByQuery extends JresJsonRequest<JresIndicesReply> {

    private @Nullable String index;
    private @Nullable String type;
    /** Single entry from {@link JresQuery#queryType()} to the JresQuery itself. */
    private final Map<String, JresQuery> query;

    public JresDeleteByQuery(@Nullable String index, JresQuery query) {
        this(index, null, query);
    }

    public JresDeleteByQuery(@Nullable String index, @Nullable String type, JresQuery query) {
        super(JresIndicesReply.class);
        this.index = index;
        this.type = type;
        this.query = ImmutableMap.of(query.queryType(), query);
    }

    @Override
    public String getHttpMethod() {
        return HttpDelete.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashedPath(index) + JresPaths.slashedPath(type) + "_query";
    }

    @Override
    public Object getPayload() {
        return query;
    }
}
