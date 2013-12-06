package com.blacklocus.jres.request.index;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.response.common.JresErrorResponseException;
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

    @Test(expected = JresErrorResponseException.class)
    public void testSad() {
        String indexName = "JresGetIndexSettingsRequestTest_testSad".toLowerCase();

        jres.request(new JresGetIndexSettingsRequest(indexName));
    }

}
