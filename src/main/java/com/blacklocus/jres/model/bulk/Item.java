package com.blacklocus.jres.model.bulk;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class Item {

    @JsonProperty
    private Boolean ok;

    @JsonProperty("_index")
    private String index;

    @JsonProperty("_type")
    private String type;

    @JsonProperty("_id")
    private String id;

    @JsonProperty("_version")
    private String version;

    private String error;

    public Boolean getOk() {
        return ok;
    }

    public String getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public boolean hasError() {
        return getError() != null;
    }

    public String getError() {
        return error;
    }
}
