package com.blacklocus.jres.request.index;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.response.common.JresErrorResponseException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresGetIndexSettingsRequestTest extends JresTest {

    @Test
    public void testHappy() {
        String indexName = "JresGetIndexSettingsRequestTest_testHappy".toLowerCase();

        jres.request(new JresCreateIndexRequest(indexName));
        jres.request(new JresGetIndexSettingsRequest(indexName));
    }

    @Test
    public void testSad() {
        String indexName = "JresGetIndexSettingsRequestTest_testSad".toLowerCase();

        try {
            jres.request(new JresGetIndexSettingsRequest(indexName));
            Assert.fail();
        } catch (JresErrorResponseException e) {
            // good
        }
    }

}
