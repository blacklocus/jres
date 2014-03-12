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
package com.blacklocus.jres.request.bulk;

import com.blacklocus.jres.request.JresBulkable;
import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.bulk.JresBulkReply;
import com.blacklocus.jres.strings.JresPaths;
import com.blacklocus.jres.strings.ObjectMappers;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.io.InputSupplier;
import org.apache.http.client.methods.HttpPost;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;

public class JresBulk extends JresJsonRequest<JresBulkReply> {

    private final @Nullable String index;
    private final @Nullable String type;
    private final Iterable<JresBulkable> actions;

    /**
     * `index` or `type` are nullable if given JresBulkables specify them. For those that don't, ElasticSearch behavior
     * is to default to the index or type given here.
     */
    public JresBulk(@Nullable String index, @Nullable String type, Iterable<JresBulkable> actions) {
        super(JresBulkReply.class);
        this.index = index;
        this.type = type;
        this.actions = actions;
    }

    @Override
    public String getHttpMethod() {
        return HttpPost.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashedPath(index) + JresPaths.slashedPath(type) + "_bulk";
    }

    @Override
    public Object getPayload() {
        // Note that while _bulk requests are made up of JSON, the body as a whole isn't actually valid JSON.
        final InputStream input;
        try {
            input = ByteStreams.join(Iterables.transform(actions, new PayloadFn())).getInput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return input;
    }

    private static final ByteSource NEW_LINE = ByteSource.wrap("\n".getBytes());

    private static class PayloadFn implements Function<JresBulkable, InputSupplier<InputStream>> {
        @Override
        public InputSupplier<InputStream> apply(JresBulkable action) {
            try {

                ByteSource lines = ByteSource.concat(
                        ByteSource.wrap(ObjectMappers.NORMAL.writeValueAsBytes(action.getAction())),
                        NEW_LINE
                );

                Object payload = action.getPayload();
                if (payload != null) {
                    lines = ByteSource.concat(
                            lines,
                            ByteSource.wrap(ObjectMappers.NORMAL.writeValueAsBytes(payload)),
                            NEW_LINE
                    );
                }

                return lines;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}