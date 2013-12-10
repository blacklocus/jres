package com.blacklocus.jres.response.index;

import com.blacklocus.jres.response.JresJsonReply;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresIndexDocumentReply extends JresJsonReply {

    private boolean ok;
    @JsonProperty("_index")
    private String index;
    @JsonProperty("_type")
    private String type;
    @JsonProperty("_id")
    private String id;
    @JsonProperty("_version")
    private String version;

}
