package com.server.utility.template;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/21/18
 * Time: 9:59 AM
 */
public class StringOperationTemplate extends BaseOperationTemplate{

    @Override
    public StringBuffer opTemplate(String objName, String attribute, String value) {
        return new StringBuffer(value);
    }
}
