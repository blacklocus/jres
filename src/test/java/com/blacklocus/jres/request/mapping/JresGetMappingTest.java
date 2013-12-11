package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresGetMappingTest extends JresTest {

    @Test(expected = JresErrorReplyException.class)
    public void sad() {
        String index = "JresGetMappingRequestTest_sad".toLowerCase();
        String type = "test";

        jres.quest(new JresGetMapping(index, type));
    }

    @Test
    public void happy() {
        String index = "JresGetMappingRequestTest_happy".toLowerCase();
        String type = "test";

        jres.quest(new JresCreateIndex(index));
        jres.quest(new JresPutMapping(index, type));

        jres.quest(new JresGetMapping(index, type));
    }
}
