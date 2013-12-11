package com.blacklocus.jres.response.bulk;

import com.blacklocus.jres.model.bulk.Item;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class JresBulkItemResult {

    public static final String ACTION_CREATE = "create";

    private final String action;
    private final Item result;

    public JresBulkItemResult(String action, Item result) {
        this.action = action;
        this.result = result;
    }

    public String getAction() {
        return action;
    }

    public Item getResult() {
        return result;
    }
}
