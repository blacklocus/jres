package com.blacklocus.jres.request.search;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.search.JresSearchReply;
import com.blacklocus.jres.strings.JresPaths;
import org.apache.http.client.methods.HttpGet;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresSearch extends JresJsonRequest<JresSearchReply> {

    private final String index;
    private final String type;

    public JresSearch(String index, String type) {
        super(JresSearchReply.class);
        this.index = index;
        this.type = type;
    }

    @Override
    public String getHttpMethod() {
        return HttpGet.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return JresPaths.slashed(index) + JresPaths.slashed(type) + "_search";
    }

    @Override
    public Object getPayload() {
        return null;
    }

}
