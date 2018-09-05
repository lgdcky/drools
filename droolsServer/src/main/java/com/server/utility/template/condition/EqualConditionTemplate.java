package com.server.utility.template.condition;

import org.apache.poi.ss.formula.functions.T;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/15/18
 * Time: 4:55 PM
 */
public class EqualConditionTemplate extends BaseConditionTemplate<T> {

    public static final String EQ = "==";

    public static final String NOTEQ = "!=";

    public static final String GT = ">";

    public static final String LT = "<";

    public static final String GTE = ">=";

    public static final String LTE = "<=";

    private String conditionKey;

    private String op;

    private String conditionValue;

    public void setConditionKey(String conditionKey) {
        this.conditionKey = conditionKey;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

    public EqualConditionTemplate(String conditionKey, String op, String conditionValue, String linkOp) {
        this.conditionKey = conditionKey;
        this.op = op;
        this.conditionValue = conditionValue;
        this.setLinkOp(linkOp);
    }

    @Override
    public String getReturnRule(String alias) {
        StringBuffer stringBuffer = new StringBuffer();
        if (null == alias) {
            stringBuffer.append(conditionKey);
        } else {
            stringBuffer.append(alias+conditionKey);
        }
        stringBuffer.append(op);
        stringBuffer.append(conditionValue);
        return stringBuffer.toString();
    }
}
