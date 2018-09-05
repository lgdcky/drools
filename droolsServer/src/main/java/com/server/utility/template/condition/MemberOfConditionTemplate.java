package com.server.utility.template.condition;

import org.apache.poi.ss.formula.functions.T;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/16/18
 * Time: 9:26 AM
 *
 *
 * 用来判断某个 Fact 对象的某个字段是否在一个集合（Collection/Array）当中
 * city memberOf "北京，上海，成都"
 *
 */
public class MemberOfConditionTemplate extends BaseConditionTemplate<T> {

    public static final String MEMBEROF = "memberOf";

    public static final String NOTMEMBEROF = "not memberOf";

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

    public MemberOfConditionTemplate(String conditionKey, String op, String conditionValue, String linkOp) {
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
