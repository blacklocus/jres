package com.blacklocus.jres.request.search.query;

import org.junit.Test;

public class JresQueryTest {

    @Test
    public void testSomeToStrings() {
        // Just make sure these toString's don't except. For humans to look at.
        System.out.println(new JresBoolQuery()
                .must(new JresQueryStringQuery("stuff:true").withDefaultOperator("AND"))
                .should(
                        new JresMatchQuery("color", "orange"),
                        new JresTermQuery("key", "abc123")
                )
                .mustNot(new JresDisMaxQuery(
                        new JresMatchQuery("color", "yellow"),
                        new JresMatchQuery("color", "green")
                ))
        );
    }
}
