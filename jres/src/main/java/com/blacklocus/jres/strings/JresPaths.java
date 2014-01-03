/**
 * Copyright 2013 BlackLocus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blacklocus.jres.strings;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresPaths {

    /**
     * @return fragment appended with '/' if not present. Does nothing to blank strings. <code>null</code>s upgraded
     * to empty strings
     */
    public static String slashed(String... fragments) {
        StringBuilder sb = new StringBuilder();
        for (String fragment : fragments) {
            sb.append(StringUtils.isBlank(fragment) || fragment.endsWith("/") ?
                    (fragment == null ? "" : fragment) :
                    fragment + "/");
        }
        return sb.toString();
    }
}
