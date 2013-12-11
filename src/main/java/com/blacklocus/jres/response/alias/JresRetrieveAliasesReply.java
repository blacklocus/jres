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
package com.blacklocus.jres.response.alias;

import com.blacklocus.jres.request.alias.JresRetrieveAliases;
import com.blacklocus.jres.response.JresJsonReply;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Response for {@link JresRetrieveAliases}.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresRetrieveAliasesReply extends JresJsonReply {

    public List<String> getIndexes() {
        return Lists.newArrayList(node().getFieldNames());
    }

    public List<String> getAliases(String index) {
        return Lists.newArrayList(node().get(index).get("aliases").getFieldNames());
    }
}
