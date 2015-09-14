(experimental...)

Jres
====
A Java ElasticSearch client library which does not have a code dependency on the entire ElasticSearch library. All
communication is JSON over HTTP.

<img src="https://travis-ci.org/blacklocus/jres.svg?branch=master"/>

### Dependency ###

maven

    <dependency>
        <groupId>com.blacklocus.jres</groupId>
        <artifactId>jres-core</artifactId>
        <version>0.1.14</version>
    </dependency>
            
gradle

    repositories {
        // ...
        mavenCentral()
    }
    dependencies {
        // ...
        compile 'com.blacklocus.jres:jres-core:0.1.14'
    }


## Usage ##

```java
public class Main {

    public static void main(String[] args) {

        // Setup an index with a mapping for documents of type MyText.
        String index = "readme_test_index";
        String type = "definition";
        String mapping = "{" +
                "  \"definition\": {" +
                "    \"properties\": {" +
                "      \"body\": {" +
                "        \"type\": \"string\"" +
                "      }" +
                "    }" +
                "  }" +
                "}";

        Jres jres = new Jres(host);
        jres.quest(new JresCreateIndex(index));
        jres.quest(new JresPutMapping(index, type, mapping));

        // Index two documents (definitions via Google)
        Definition cat = new Definition("cat",
                "a small domesticated carnivorous mammal " +
                        "with soft fur, a short snout, and retractile claws. It is " +
                        "widely kept as a pet or for catching mice, and many breeds " +
                        "have been developed.");
        Definition dog = new Definition("dog",
                "a domesticated carnivorous mammal that " +
                        "typically has a long snout, an acute sense of smell, and a " +
                        "barking, howling, or whining voice. It is widely kept as a pet " +
                        "or for work or field sports.");
        jres.quest(new JresIndexDocument(index, type, cat));
        jres.quest(new JresIndexDocument(index, type, dog));

        // Do some querying
        JresMatchQuery retractileQuery = new JresMatchQuery("body", "retractile");
        JresMatchQuery mammalQuery = new JresMatchQuery("body", "mammal");
        JresMatchQuery alligatorQuery = new JresMatchQuery("body", "alligator");
        JresBoolQuery retractileAndMammalQuery = new JresBoolQuery()
                .must(retractileQuery, mammalQuery);

        // We need to flush the queue in ElasticSearch otherwise our recently
        // submitted documents may not yet be indexed.
        jres.quest(new JresFlush(index));

        JresSearchReply reply;

        System.out.println("Searching 'retractile'");
        reply = jres.quest(new JresSearch(index, type, new JresSearchBody()
                .query(retractileQuery)));
        for (Hit hit : reply.getHits().getHits()) {
            Definition definition = hit.getSourceAsType(Definition.class);
            System.out.println("  Found " + definition.term);
        }

        System.out.println("Searching 'mammal'");
        reply = jres.quest(new JresSearch(index, type, new JresSearchBody()
                .query(mammalQuery)));
        for (Hit hit : reply.getHits().getHits()) {
            Definition definition = hit.getSourceAsType(Definition.class);
            System.out.println("  Found " + definition.term);
        }

        System.out.println("Searching 'alligator'");
        reply = jres.quest(new JresSearch(index, type, new JresSearchBody()
                .query(alligatorQuery)));
        if (reply.getHits().getTotal() == 0) {
            System.out.println("  Nothing found about alligators");
        }

        System.out.println("Searching 'retractile' and 'mammal'");
        reply = jres.quest(new JresSearch(index, type, new JresSearchBody()
                .query(retractileAndMammalQuery)));
        for (Hit hit : reply.getHits().getHits()) {
            Definition definition = hit.getSourceAsType(Definition.class);
            System.out.println("  Found " + definition.term);
        }
    }

    // This must be (de)serializable by Jackson. I have chosen to go with
    // public fields, and provided a non-private default constructor.
    // This is just one of many ways to enable Jackson (de)serialization.
    static class Definition {
        public String term;
        public String body;

        Definition() {
        }

        public Definition(String term, String body) {
            this.term = term;
            this.body = body;
        }

        @Override
        public String toString() {
            return term + ": " + body;
        }
    }
}
```

This program produces this output.

```
Searching 'retractile'
  Found cat
Searching 'domestic'
  Found cat
  Found dog
Searching 'alligator'
  Nothing found about alligators
Searching 'retractile' and 'mammal'
  Found cat
```


### More ###

Select ElasticSearch APIs are wrapped up in corresponding request objects that implement
[`JresRequest`](https://github.com/blacklocus/jres/tree/master/jres/src/main/java/com/blacklocus/jres/request/JresRequest.java).
All such request objects are located in the `com.blacklocus.jres.request` package tree.

Notice that the request object captures what its
[`JresReply`](https://github.com/blacklocus/jres/tree/master/jres/src/main/java/com/blacklocus/jres/response/JresReply.java)
should be as a type parameter. Because the total ElasticSearch API is rather large, this transforms API bindings into
the form of representative classes (implementors of JresRequest&lt;JresReply&gt;), instead of requiring a large number
of methods on the Jres object to represent each unique ElasticSearch API call.


### jres.quest ###

This will likely be the primary invocation on a Jres object. It covers most kinds of requests and those that would be
used most frequently such as indexing documents. It accepts JresRequests whose correlated response extends `JresJsonReply`.
These are ElasticSearch API calls that return JSON (not all ElasticSearch APIs return JSON), and so those responses
can be deserialized into representative objects. If the HTTP response status code is not ok, then a
`JresErrorReplyException` will be thrown. An example follows for indexing a document.

[ElasticSearch API Index Document](http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-index_.html)
has been captured as
[`JresIndexDocument`](https://github.com/blacklocus/jres/tree/master/jres/src/main/java/com/blacklocus/jres/request/JresRequest.java)
This example is obscenely verbose for illustration.

    Jres jres = new Jres(Suppliers.ofInstance("http://elasticsearchhost:9200/"));

    UserCommentPojo document = new UserCommentPojo();
    document.name = "test doc";
    document.uploaded = System.currentTimeMillis();
    document.content = "Hi, Mom.";

    String elasticSearchIndex = "primarySite";
    String elasticSearchDocumentType = "userComment";

Usually I find that IDE tooling helps immensely here. Lay out your request without variable capture.

    jres.quest(new JresIndexDocument(elasticSearchIndex, elasticSearchDocumentType, document))

Then use your tooling to figure out what the reply object is, rather than inspecting the request class yourself.
For IntelliJ, use [Extract Variable Refactor](http://www.jetbrains.com/idea/webhelp/extract-variable.html).

    JresIndexDocumentReply reply = jres.quest(new JresIndexDocument(elasticSearchIndex, elasticSearchDocumentType, document))

    // At this time, these objects should satisfy the default configuration of the Jackson `ObjectMapper`, i.e.
    // public fields or standard bean property getters/setters.
    class UserCommentPojo {
        public String name;
        public Long uploaded;
        public String contents;
    }


### jres.tolerate ###

is identical to `jres.quest` but tolerates exception responses. If there was an exception response and the HTTP response
status code matches the given tolerated status code, that is captured in the return. This is useful where an error
response might be expected without involving expensive and laborious exception handling.


### jres.bool ###

represents ElasticSearch APIs that return a boolean response in the form of HTTP status codes (200 or 404). Negative
responses should translate to false return values rather than JresErrorReplyExceptions.


----

Plenty more examples can be observed in the request object unit tests:
[jres-test/src/test/java/com.blacklocus.jres.request](https://github.com/blacklocus/jres/tree/master/jres-test/src/test/java/com/blacklocus/jres/request)



## License ##

Copyright 2014 BlackLocus under [the Apache 2.0 license](LICENSE)
