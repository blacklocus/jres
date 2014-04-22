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
package com.blacklocus.jres.request.search;

import com.blacklocus.jres.Jres;
import com.blacklocus.jres.request.search.facet.JresFacet;
import com.blacklocus.jres.request.search.query.JresBoolQuery;
import com.blacklocus.jres.request.search.query.JresQuery;
import com.blacklocus.jres.request.search.sort.JresSort;
import com.blacklocus.jres.strings.ObjectMappers;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class JresSearchBody {

    /**
     * Single entry from {@link JresQuery#queryType()} to the JresQuery itself. Values are objects to support
     * {@link Jres#load(URL, Class)} which isn't smartened up to determine a query's corresponding JresQuery subclass.
     */
    private Map<String, Object> query;
    private List<String> fields;
    private Map<String, Object> facets;
    private Integer size;
    /**
     * Each map is single-keyed from {@link JresSort#sortType()} to the JresSort itself. Values are objects to support
     * {@link Jres#load(URL, Class)} which isn't smartened up to determine a query's corresponding JresQuery subclass.
     */
    private List<Map<String, Object>> sort;

    public JresSearchBody query(JresQuery query) {
        this.query = ImmutableMap.<String, Object>of(query.queryType(), query);
        return this;
    }

    /**
     * @param queryType e.g. "match", "bool", "term", ...
     * @param queryParams the body of that query, e.g. for "match" might be simply
     *                    <code>ImmutableMap.of("field", "find this value")</code>
     */
    public JresSearchBody query(String queryType, Map<String, ?> queryParams) {
        this.query = ImmutableMap.<String, Object>of(queryType, queryParams);
        return this;
    }

    /**
     * Replaces the current set of fields in this search. Note that invoking this method with no arguments will set
     * the list of fields to an empty list, which is different than not setting it at all, which would tell
     * elasticsearch to return the default set of fields.
     */
    public JresSearchBody fields(String... fields) {
        this.fields = ImmutableList.<String>builder().add(fields).build();
        return this;
    }

    /**
     * Replaces the current set of facets in this search.
     */
    public JresSearchBody facets(JresFacet... facets) {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        for (JresFacet facet : facets) {
            builder.put(facet.facetName(), ImmutableMap.of(facet.facetType(), facet));
        }
        this.facets = builder.build();
        return this;
    }

    /**
     * Sets the max size of the query results for this search.
     */
    public JresSearchBody size(Integer size) {
        this.size = size;
        return this;
    }

    /**
     * Replaces the current set of sorts in this search.
     */
    public JresSearchBody sort(JresSort... sorts) {
        ImmutableList.Builder<Map<String, Object>> builder = ImmutableList.builder();
        for (JresSort sort : sorts) {
            builder.add(ImmutableMap.<String, Object>of(sort.sortType(), sort));
        }
        this.sort = builder.build();
        return this;
    }

    /**
     * A means to pull the polymorphic, contained {@link JresQuery} out of this search body. If this JresSearchBody has
     * previously been given {@link #query(JresQuery)} of type {@link JresBoolQuery}, you can get the contained
     * JresBoolQuery back out via <code>JresBoolQuery bool = searchBody.getQuery(JresBoolQuery.class);</code>
     *
     * @param queryClass instance of the type of query of this search
     * @throws RuntimeException if the asked-for type is not the contained query type.
     */
    @SuppressWarnings("unchecked")
    public <T extends JresQuery> T getQuery(Class<T> queryClass) throws RuntimeException {
        final T sampleInstance;
        try {
            sampleInstance = queryClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assert getQuery().size() <= 1;

        // Polymorphism makes handling this a little tricky. Check the query key, e.g. "bool", "term", ...
        if (!sampleInstance.queryType().equals(getQuery().keySet().iterator().next())) {
            throw new RuntimeException("Contained query is not of the given type, " + queryClass);
        }

        Object queryObj = getQuery().get(sampleInstance.queryType());
        JsonNode queryNode = ObjectMappers.NORMAL.valueToTree(queryObj);
        T querySubtype = ObjectMappers.fromJson(queryNode, queryClass);
        // Swap this instance in, so we can return the one that the caller can modify.
        getQuery().put(querySubtype.queryType(), querySubtype);
        return querySubtype;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serializable getter properties

    /**
     * see {@link #getQuery(Class)} to retrieve the contained JresQuery
     */
    public Map<String, Object> getQuery() {
        return query;
    }

    public List<String> getFields() {
        return fields;
    }

    public Map<String, Object> getFacets() {
        return facets;
    }

    public Integer getSize() {
        return size;
    }

    public List<Map<String, Object>> getSort() {
        return sort;
    }
}
