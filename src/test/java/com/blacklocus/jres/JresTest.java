package com.blacklocus.jres;

import com.google.common.base.Suppliers;
import org.junit.BeforeClass;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresTest {

    @BeforeClass
    public static void startLocalElasticSearch() {
        ElasticSearchTestInstance.triggerStaticInit();
    }

    protected Jres jres = new Jres(Suppliers.ofInstance("http://localhost:9201"));

}
