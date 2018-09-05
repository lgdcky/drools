package com.server.utility.template;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/20/18
 * Time: 10:44 PM
 */
public class SimpleOperationTemplate extends BaseOperationTemplate {

    @Override
    public StringBuffer opTemplate(String objName,String attribute,String value) {
        StringBuffer op = new StringBuffer();
        return op.append("$"+objName.toLowerCase()+".set"+attribute+"("+value+");");
    }
}
