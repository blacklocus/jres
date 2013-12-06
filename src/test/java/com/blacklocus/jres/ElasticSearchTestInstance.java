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
 *
 * @author Jason Dunkelberger (dirkraft)
 */
class ElasticSearchTestInstance {
    static void triggerStaticInit(){}

    static final Node node;

    static {
        File dir = new File(System.getProperty("java.io.tmpdir") + "/ElasticSearchTestInstance");
        if (dir.exists()) {
            FileUtils.deleteQuietly(dir);
            dir.mkdirs();
        }
        Settings settings = ImmutableSettings.settingsBuilder().put("http.port", 9201).put("node.http.enabled", true)
                .put("index.number_of_shards", 1).put("index.number_of_replicas", 0)
                .put("path.data", dir.getAbsolutePath()).build();

        node = NodeBuilder.nodeBuilder().local(true).settings(settings).clusterName("junit_test_cluster").node();
        node.start();
        node.client().admin().cluster().prepareHealth().setWaitForGreenStatus().execute().actionGet();
    }

}
