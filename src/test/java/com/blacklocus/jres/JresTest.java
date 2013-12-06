package com.blacklocus.jres;

import com.google.common.base.Suppliers;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Extend this to be guaranteed that a test ElasticSearch instance will be available for usage with the member
 * {@link #jres}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresTest {

    @BeforeClass
    public static void startLocalElasticSearch() {
        ElasticSearchTestInstance.triggerStaticInit();
    }

    /**
     * Configured to connect to a local ElasticSearch instance created specifically for unit testing
     */
    protected Jres jres = new Jres(Suppliers.ofInstance("http://localhost:9201"));

}
