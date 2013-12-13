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

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.common.JresAcknowledgedReply;
import org.apache.http.client.methods.HttpDelete;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresDeleteIndex extends JresJsonRequest<JresAcknowledgedReply> {

    private final String index;

    public JresDeleteIndex(String index) {
        super(JresAcknowledgedReply.class);
        this.index = index;
    }

    @Override
    public String getHttpMethod() {
        return HttpDelete.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return index;
    }

    @Override
    public Object getPayload() {
        return null;
    }
}
