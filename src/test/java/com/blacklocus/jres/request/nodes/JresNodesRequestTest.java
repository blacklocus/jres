package com.blacklocus.jres.request.nodes;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.nodes.JresNodesRequest;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresNodesRequestTest extends JresTest {

    @Test
    public void testHappy() {
        jres.request(new JresNodesRequest());
    }
}
