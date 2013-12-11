package com.blacklocus.jres.request.index;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.response.common.JresShardsReply;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresRefreshTest extends JresTest {
    
    @Test
    public void test() {
        String index = "JresRefreshTest_test".toLowerCase();

        jres.quest(new JresCreateIndex(index));
        JresShardsReply refreshReply = jres.quest(new JresRefresh(index));
        Assert.assertTrue(refreshReply.getOk());
    }
}
