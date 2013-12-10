package com.blacklocus.jres.request.mapping;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.index.JresCreateIndex;
import com.blacklocus.jres.response.JresBooleanReply;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresTypeExistsRequestTest extends JresTest {

    @Test
    public void happy() {
        String index = "JresTypeExistsRequestTest_happy".toLowerCase();
        String type = "test";

        jres.quest(new JresCreateIndex(index));
        jres.quest(new JresPutMapping(index, type));

        JresBooleanReply response = jres.bool(new JresTypeExists(index, type));
        Assert.assertTrue(response.verity());
    }

    @Test
    public void sad() {
        String index = "JresTypeExistsRequestTest_sad".toLowerCase();
        String type = "test";

        JresBooleanReply response = jres.bool(new JresTypeExists(index, type));
        Assert.assertFalse(response.verity());

        jres.quest(new JresCreateIndex(index));
        response = jres.bool(new JresTypeExists(index, type));
        Assert.assertFalse(response.verity());
    }
}
