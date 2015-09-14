/**
 * Copyright 2015 BlackLocus
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blacklocus.jres;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import java.io.File;

/**
 * This is a thread-safe way to initialize a singleton resource. The class just needs to be referenced by whatever
 * execution entry path.
 */
class ElasticSearchTestInstance {

    static void triggerStaticInit() {
    }

    static final Node node;

    static {
        File dir = new File(System.getProperty("java.io.tmpdir") + "/ElasticSearchTestInstance");
        if (dir.exists()) {
            FileUtils.deleteQuietly(dir);
            dir.mkdirs();
        }
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("http.port", 9201)
                .put("node.http.enabled", true)
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 0)
                .put("path.data", dir.getAbsolutePath())
                .put("script.disable_dynamic", false)
                .build();

        node = NodeBuilder.nodeBuilder().local(true).settings(settings).clusterName("junit_test_cluster").node();
        node.start();
        node.client().admin().cluster().prepareHealth().setWaitForGreenStatus().execute().actionGet();
    }

}
