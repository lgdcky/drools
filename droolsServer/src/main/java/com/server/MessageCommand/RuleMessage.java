package com.server.MessageCommand;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 6:44 PM
 */
public class RuleMessage<T> {

    private String message;

    private String error;

    private boolean isSuccess;

    private List<T> data;

    public RuleMessage(String message, String error, boolean isSuccess, List<T> data) {
        this.message = message;
        this.error = error;
        this.isSuccess = isSuccess;
        this.data = data;
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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
