package com.server.MessageCommand;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/19/18
 * Time: 3:26 PM
 */
public class KnowledgeMessage implements Serializable {

    public static final String SUCCESS = "success";

    public static final String FAILED = "failed";

    private String message;

    private String state;

    private String error;

    public KnowledgeMessage(String message, String state,String error) {
        this.message = message;
        this.state = state;
        this.error = error;
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
