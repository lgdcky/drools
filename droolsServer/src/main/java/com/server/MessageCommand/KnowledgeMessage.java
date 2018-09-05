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


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
