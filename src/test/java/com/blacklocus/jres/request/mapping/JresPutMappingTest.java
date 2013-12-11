package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.response.JresBooleanReply;
import com.blacklocus.jres.response.common.JresAcknowledgedReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresPutMappingTest extends JresTest {

    @Test
    public void sad() {
        String index = "JresPutMappingRequestTest_sad".toLowerCase();
        String type = "test";

        try {
            jres.quest(new JresPutMapping(index, type, "{\"test\":{}}"));
            Assert.fail("Shouldn't be able to put type mapping on non-existent index");
        } catch (JresErrorReplyException e) {
            // good
        }

        jres.quest(new JresCreateIndex(index));

        try {
            jres.quest(new JresPutMapping(index, type, null));
            Assert.fail("Invalid data");
        } catch (JresErrorReplyException e) {
            // good
        }

        try {
            jres.quest(new JresPutMapping(index, type, ""));
            Assert.fail("Invalid data");
        } catch (JresErrorReplyException e) {
            // good
        }

        try {
            jres.quest(new JresPutMapping(index, type, "{}"));
            Assert.fail("Invalid data");
        } catch (JresErrorReplyException e) {
            // good
        }
    }

    @Test
    public void happy() {
        String index = "JresPutMappingRequestTest_happy".toLowerCase();
        String type = "test";

        {
            JresBooleanReply response = jres.bool(new JresTypeExists(index, type));
            Assert.assertFalse(response.verity());
        }

        jres.quest(new JresCreateIndex(index));
        {
            JresAcknowledgedReply response = jres.quest(new JresPutMapping(index, type, "{\"test\":{}}"));
            Assert.assertTrue(response.getOk() && response.isAcknowledged());
        }

        {
            JresBooleanReply response = jres.bool(new JresTypeExists(index, type));
            Assert.assertTrue(response.verity());
        }
    }
}
