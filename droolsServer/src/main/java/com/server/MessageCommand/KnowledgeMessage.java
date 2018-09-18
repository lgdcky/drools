package com.server.MessageCommand;


import org.kie.internal.builder.KnowledgeBuilder;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/19/18
 * Time: 3:26 PM
 */
public class KnowledgeMessage implements Serializable {

    private String message;

    private String state;

    public void KnowledgeMessage(String message, String state) {
        this.message = message;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
