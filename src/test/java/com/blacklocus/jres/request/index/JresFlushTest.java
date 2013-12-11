package com.blacklocus.jres.request.index;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.response.common.JresShardsReply;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresFlushTest extends JresTest {
    
    @Test
    public void test() {
        String index = "JresFlushTest_test".toLowerCase();

        jres.quest(new JresCreateIndex(index));
        JresShardsReply flushReply = jres.quest(new JresFlush(index));
        Assert.assertTrue(flushReply.getOk());
    }
}
