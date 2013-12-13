package com.blacklocus.jres.request;

import com.blacklocus.jres.BaseJresTest;
import com.blacklocus.jres.Jres;
import com.blacklocus.jres.request.index.JresDeleteIndex;
import com.blacklocus.jres.response.common.JresAcknowledgedReply;
import com.blacklocus.jres.response.common.JresErrorReplyException;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public final class JresTest extends BaseJresTest { // final to prevent accidentally extending instead of BaseJresTest

    @Test(expected = JresErrorReplyException.class)
    public void testExcept() {
        jres.quest(new JresDeleteIndex("JresTest.testExcept".toLowerCase()));
    }

    @Test
    public void testTolerate() {
        String index = "JresTest.testExcept".toLowerCase();
        Integer toleratedStatus = HttpStatus.SC_NOT_FOUND;

        Jres.Tolerance<JresAcknowledgedReply> tolerance = jres.tolerate(new JresDeleteIndex(index), toleratedStatus);
        Assert.assertTrue(tolerance.isError());
        Assert.assertNull(tolerance.getReply());
        Assert.assertNotNull(tolerance.getError());
        Assert.assertEquals(toleratedStatus, tolerance.getError().getStatus());
    }
}
