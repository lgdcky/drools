package com.server.utility.template;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/20/18
 * Time: 10:44 PM
 */
public abstract class BaseOperationTemplate {

    public static final String SIMPLEOPERATIONTYPE = "SIMPLEOPERATION";

    public static final String STRINGYPE = "STRING";

    public abstract StringBuffer opTemplate(String objName,String attribute,String value);

}
