package com.blacklocus.jres.request.index;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresGetIndexSettingsTest extends JresTest {

    @Test
    public void testHappy() {
        String indexName = "JresGetIndexSettingsRequestTest_testHappy".toLowerCase();

        jres.quest(new JresCreateIndex(indexName));
        jres.quest(new JresGetIndexSettings(indexName));
    }

    @Test(expected = JresErrorReplyException.class)
    public void testSad() {
        String indexName = "JresGetIndexSettingsRequestTest_testSad".toLowerCase();

        jres.quest(new JresGetIndexSettings(indexName));
    }

}
