package com.blacklocus.jres.request.index;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.response.JresBooleanReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresCreateIndexTest extends JresTest {

    @Test(expected = JresErrorReplyException.class)
    public void sad() {
        // index names must be lowercase
        String indexName = "JresCreateIndexRequestTest_sad";

        JresBooleanReply indexExistsResponse = jres.bool(new JresIndexExists(indexName));
        Assert.assertFalse(indexExistsResponse.verity());

        jres.quest(new JresCreateIndex(indexName));
    }

    @Test
    public void happy() {
        String indexName = "JresCreateIndexRequestTest_happy".toLowerCase();

        JresBooleanReply indexExistsResponse = jres.bool(new JresIndexExists(indexName));
        Assert.assertFalse(indexExistsResponse.verity());

        jres.quest(new JresCreateIndex(indexName));
        indexExistsResponse = jres.bool(new JresIndexExists(indexName));
        Assert.assertTrue(indexExistsResponse.verity());
    }
}
