package com.blacklocus.jres.model.search;

import com.blacklocus.jres.strings.ObjectMappers;
import org.codehaus.jackson.JsonNode;

import java.util.HashMap;

public class Facets extends HashMap<String, JsonNode> {

    public <T extends Facet> T get(String facetName, Class<T> klass) {
        return ObjectMappers.fromJson(get(facetName), klass);
    }

}
