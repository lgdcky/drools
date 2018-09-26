package com.server.MessageCommand;

import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 3:19 PM
 */
public class FactMessage<T> implements Serializable {

    public static final String SUCCESS = "success";

    public static final String FAILED = "failed";

    private String state;

    private String error;

    private String message;

    private boolean isBusiness;

    private List<T> data;

    public FactMessage(String state, String error, String message, boolean isBusiness, List<T> data) {
        this.state = state;
        this.error = error;
        this.message = message;
        this.isBusiness = isBusiness;
        this.data = data;
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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
