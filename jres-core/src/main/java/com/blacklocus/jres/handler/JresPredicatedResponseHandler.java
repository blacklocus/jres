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
package com.blacklocus.jres.handler;

import com.blacklocus.jres.response.JresBooleanReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import com.google.common.base.Predicate;
import org.apache.http.HttpResponse;

import java.io.IOException;

public class JresPredicatedResponseHandler extends AbstractJresResponseHandler<JresBooleanReply> {

    private final Predicate<HttpResponse> predicate;

    public JresPredicatedResponseHandler(Predicate<HttpResponse> predicate) {
        super(JresBooleanReply.class);
        this.predicate = predicate;
    }

    @Override
    public JresBooleanReply handleResponse(HttpResponse http) throws IOException, JresErrorReplyException {
        return new JresBooleanReply(predicate.apply(http));
    }

}
