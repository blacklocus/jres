package com.blacklocus.jres.request.index;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.response.JresBooleanResponse;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresIndexExistsRequestTest extends JresTest {

    @Test
    public void testHappy() {
        String index = "JresIndexExistsRequestTest".toLowerCase();

        JresBooleanResponse res = jres.request(new JresIndexExistsRequest(index));
        Assert.assertFalse(res.verity());

        jres.request(new JresCreateIndexRequest(index));
        res = jres.request(new JresIndexExistsRequest(index));
        Assert.assertTrue(res.verity());

    }
}
