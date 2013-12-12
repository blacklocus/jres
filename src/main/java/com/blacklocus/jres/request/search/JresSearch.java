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
import com.blacklocus.jres.response.search.JresSearchReply;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpPost;

import javax.annotation.Nullable;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresSearch extends JresJsonRequest<JresSearchReply> {

    private String index;
    private String type;
    private final JresSearchBody searchBody;

    public JresSearch() {
        this(new JresSearchBody());
    }

    public JresSearch(JresSearchBody searchBody) {
        this(null, null, searchBody);
    }

    public JresSearch(@Nullable String index, @Nullable String type) {
        this(index, type, new JresSearchBody());
    }

    public JresSearch(@Nullable String index, @Nullable String type, JresSearchBody searchBody) {
        super(JresSearchReply.class);
        this.searchBody = searchBody;
        this.index = index;
        this.type = type;
    }

    @Override
    public String getHttpMethod() {
        return HttpPost.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(index) + JresPaths.slashed(type) + "_search";
    }

    @Override
    public Object getPayload() {
        return searchBody;
    }

}
