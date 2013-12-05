package com.blacklocus.jres.request;

import com.blacklocus.jres.JresTest;
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
