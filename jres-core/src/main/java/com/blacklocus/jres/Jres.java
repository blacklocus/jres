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
package com.blacklocus.jres;

import com.blacklocus.jres.request.JresBooleanRequest;
import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.JresBooleanReply;
import com.blacklocus.jres.response.JresJsonReply;
import com.blacklocus.jres.response.JresReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;

public interface Jres {

    <REQUEST extends JresBooleanRequest> JresBooleanReply bool(REQUEST request);

    <REPLY extends JresJsonReply, QUEST extends JresJsonRequest<REPLY>> REPLY quest(QUEST quest);

    <REPLY extends JresJsonReply, QUEST extends JresJsonRequest<REPLY>> Tolerance<REPLY> tolerate(QUEST quest, int toleratedStatus);

    /**
     * @param <REPLY> response - type of response object produced for a successful ElasticSearch response. Currently assumes
     *                   a JsonNode basis.
     */
    public static class Tolerance<REPLY extends JresReply> {
        // Assume JsonNode basis for now, keep exposed signatures' generics simpler.

        private final boolean error;
        private final JresErrorReplyException exception;
        private final REPLY reply;

        Tolerance(boolean error, REPLY reply) {
            this.error = error;
            this.reply = reply;
            this.exception = null;
        }

        Tolerance(boolean error, JresErrorReplyException exception) {
            this.error = error;
            this.exception = exception;
            this.reply = null;
        }

        /**
         * @return whether or not an error response was captured: <code>true</code> {@link #getError()} is not null,
         * <code>false</code> {@link #getReply()} is not null
         */
        public boolean isError() {
            return error;
        }

        /**
         * @return captured {@link JresErrorReplyException}, if the request resulted in an error response
         */
        public JresErrorReplyException getError() {
            return exception;
        }

        /**
         * @return captured {@link JresReply}, if the request was ok
         */
        public REPLY getReply() {
            return reply;
        }

    }
}
