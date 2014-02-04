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
package com.blacklocus.jres.model.search;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Facet {

    @JsonProperty("_type")
    private String type;

    private Long missing;
    private Long total;
    private Long other;

    public String getType() {
        return type;
    }

    public Long getMissing() {
        return missing;
    }

    public Long getTotal() {
        return total;
    }

    public Long getOther() {
        return other;
    }
}
