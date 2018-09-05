package com.server.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/23/18
 * Time: 5:52 PM
 */
public class FactClassDescriptionInfo {

    private String className;

    private String description;

    private List<FactFieldDescriptionInfo> factFieldDescriptionInfoList;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FactFieldDescriptionInfo> getFactFieldDescriptionInfoList() {
        return factFieldDescriptionInfoList;
    }

    public void setFactFieldDescriptionInfoList(List<FactFieldDescriptionInfo> factFieldDescriptionInfoList) {
        this.factFieldDescriptionInfoList = factFieldDescriptionInfoList;
    }
}
