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

    <REPLY extends JresJsonReply, QUEST extends JresJsonRequest<REPLY>> JresImpl.Tolerance<REPLY> tolerate(QUEST quest, int toleratedStatus);

    /**
     * @param <REPLY> response - type of response object produced for a successful ElasticSearch response. Currently assumes
     *                   a JsonNode basis.
     */
    class Tolerance<REPLY extends JresReply> {
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
