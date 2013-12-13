package com.blacklocus.jres.request.index;

import com.blacklocus.jres.request.JresJsonRequest;
import com.blacklocus.jres.response.common.JresAcknowledgedReply;
import org.apache.http.client.methods.HttpDelete;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresDeleteIndex extends JresJsonRequest<JresAcknowledgedReply> {

    private final String index;

    public JresDeleteIndex(String index) {
        super(JresAcknowledgedReply.class);
        this.index = index;
    }

    @Override
    public String getHttpMethod() {
        return HttpDelete.METHOD_NAME;
    }

    @Override
    public String getPath() {
        return index;
    }

    @Override
    public Object getPayload() {
        return null;
    }
}
