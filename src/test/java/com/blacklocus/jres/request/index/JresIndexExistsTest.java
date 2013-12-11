package com.blacklocus.jres.request.index;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.response.JresBooleanReply;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresIndexExistsTest extends JresTest {

    @Test
    public void testHappy() {
        String index = "JresIndexExistsRequestTest".toLowerCase();

        JresBooleanReply res = jres.bool(new JresIndexExists(index));
        Assert.assertFalse(res.verity());

        jres.quest(new JresCreateIndex(index));
        res = jres.bool(new JresIndexExists(index));
        Assert.assertTrue(res.verity());

    }
}
