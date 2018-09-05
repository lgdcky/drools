package com.server.utility.template.condition;

import org.apache.poi.ss.formula.functions.T;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/16/18
 * Time: 9:12 AM
 * <p>
 * <p>
 * <p>
 * <p>
 * 用来检查一个Fact 对象的某个字段(该字段要是一个Collection或是一个Array)是否包含一个指定对象
 * 例:"北京，上海，成都" contains city
 */
public class ContainsConditionTemplate extends BaseConditionTemplate<T> {

    public static final String CONTAINS = "contains";

    public static final String NOTCONTAINS = "not contains";

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


    /**
     * @param conditionKey
     * @param op
     * @param conditionValue
     */
    public ContainsConditionTemplate(String conditionKey, String op, String conditionValue, String linkOp) {
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
        stringBuffer.append(" " + op + " ");
        stringBuffer.append(conditionValue);
        return stringBuffer.toString();
    }
}
