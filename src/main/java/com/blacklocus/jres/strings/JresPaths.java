package com.blacklocus.jres.strings;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresPaths {

    public static String slashed(String fragment) {
        return fragment.endsWith("/") ? fragment : fragment + "/";
    }
}
