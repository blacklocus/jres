package com.blacklocus.jres.model.search;

import com.blacklocus.jres.strings.ObjectMappers;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class Hit {

    @JsonProperty("_index")
    private String index;

    @JsonProperty("_type")
    private String type;

    @JsonProperty("_id")
    private String id;

    @JsonProperty("_score")
    private Double score;

    @JsonProperty("_source")
    private JsonNode source;


    public String getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Double getScore() {
        return score;
    }

    public JsonNode getSource() {
        return source;
    }

    public <T> T getSourceAsType(TypeReference<T> typeReference) {
        try {
            return ObjectMappers.NORMAL.readValue(source, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
