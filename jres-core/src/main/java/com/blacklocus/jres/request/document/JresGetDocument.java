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
package com.blacklocus.jres.request.document;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.document.JresGetDocumentReply;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpGet;

public class JresGetDocument extends JresJsonRequest<JresGetDocumentReply> {

    private final String index;
    private final String type;
    private final String id;

    public JresGetDocument(String index, String type, String id) {
        super(JresGetDocumentReply.class);
        this.index = index;
        this.type = type;
        this.id = id;
    }

    @Override
    public String getHttpMethod() {
        return HttpGet.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashedPath(index, type) + id;
    }

    @Override
    public Object getPayload() {
        return null;
    }

}
