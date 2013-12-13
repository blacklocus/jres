package com.blacklocus.jres.request.index;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.response.common.JresAcknowledgedReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresDeleteIndexTest extends JresTest {

    @Test
    public void testExisting() {
        String index = "JresDeleteIndexTest.testExisting".toLowerCase();
        jres.quest(new JresCreateIndex(index));
        Assert.assertTrue(jres.bool(new JresIndexExists(index)).verity());

        JresAcknowledgedReply deleteReply = jres.quest(new JresDeleteIndex(index));
        Assert.assertTrue(deleteReply.getOk());
        Assert.assertTrue(deleteReply.getAcknowledged());
    }

    @Test(expected = JresErrorReplyException.class)
    public void testMissing() {
        String index = "JresDeleteIndexTest.testMissing".toLowerCase();

        jres.quest(new JresDeleteIndex(index));
    }
}
