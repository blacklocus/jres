package com.blacklocus.jres.request;

/**
 * ElasticSearch models most operations as actions, and many (all?) actions can often be chained into a bulk request.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public interface JresBulkable {

    /**
     * @return bulk command
     */
    Object getAction();

    /**
     * @return (optional) payload of action, e.g. index operation is followed by the document to be indexed
     */
    Object getPayload();


}
