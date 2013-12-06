package com.blacklocus.jres.request.index;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.response.index.JresIndexExistsResponse;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresIndexExistsRequestTest extends JresTest {

    @Test
    public void test() {
        JresIndexExistsResponse res = jres.request(new JresIndexExistsRequest("JresIndexExistsRequestTest"));

        // TODO

    }
}
