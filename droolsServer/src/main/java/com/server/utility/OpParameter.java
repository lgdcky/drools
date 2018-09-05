package com.server.utility;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/20/18
 * Time: 10:55 PM
 */
public class OpParameter {

    public static final String SIMPLE = "simple";

    @NotNull
    private String type;

    private String objName;

    private String attribute;

    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
