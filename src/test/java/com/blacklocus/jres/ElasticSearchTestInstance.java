package com.blacklocus.jres;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

/**
 * This is a thread-safe way to initialize a singleton resource. The class just needs to be referenced by whatever
 * execution entry path.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
class ElasticSearchTestInstance {
    static void triggerStaticInit(){}

    static {
        Settings settings = ImmutableSettings.settingsBuilder().put("http.port", 9201).put("node.http.enabled", true)
                .put("index.number_of_shards", 1).put("index.number_of_replicas", 0).build();

        Node node = NodeBuilder.nodeBuilder().local(true).settings(settings).clusterName("junit_test_cluster").node();
        node.start();
        node.client().admin().cluster().prepareHealth().setWaitForGreenStatus().execute().actionGet();
    }
}
