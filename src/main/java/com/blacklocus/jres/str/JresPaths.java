package com.blacklocus.jres.str;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresPaths {

    public static String slashed(String fragment) {
        return fragment.endsWith("/") ? fragment : fragment + "/";
    }
}
