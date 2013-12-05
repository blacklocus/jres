package com.blacklocus.jres.request;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.response.JresNodesResponse;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresNodesRequestTest extends JresTest {

    @Test
    public void test() {
        jres.request(new JresNodesRequest());
    }
}
