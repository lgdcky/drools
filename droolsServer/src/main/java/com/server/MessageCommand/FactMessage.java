package com.server.MessageCommand;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 3:19 PM
 */
public class FactMessage implements Serializable {

    private String state;

    private String error;

    private String message;

    private boolean isBusiness;

    public FactMessage(String state, String error, String message, boolean isBusiness) {
        this.state = state;
        this.error = error;
        this.message = message;
        this.isBusiness = isBusiness;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
