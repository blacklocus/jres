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
package com.blacklocus.jres.response.bulk;

import com.blacklocus.jres.model.bulk.Item;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresBulkItemResult {

    public static final String ACTION_CREATE = "create";
    public static final String ACTION_INDEX = "index";

    private final String action;
    private final Item result;

    public JresBulkItemResult(String action, Item result) {
        this.action = action;
        this.result = result;
    }

    public String getAction() {
        return action;
    }

    public Item getResult() {
        return result;
    }
}
