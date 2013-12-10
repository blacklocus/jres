package com.blacklocus.jres.request;

import com.blacklocus.jres.JresTest;
import com.blacklocus.jres.request.index.JresCreateIndex;
import org.junit.Test;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresSearchRequestTest extends JresTest {

    @Test
    public void test() {
        String index = "JresSearchRequestTest".toLowerCase();

        jres.quest(new JresCreateIndex(index));

        // TODO
    }
}
