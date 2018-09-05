package com.server.utility.template.condition;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/15/18
 * Time: 3:35 PM
 */
public class RangeConditionTemplate extends BaseConditionTemplate<T> {

    public static final String TYPE = "range";

    public static final String IN = "in";

    public static final String NOTIN = "not in";

    private String conditionKey;

    private String op;

    private String rangeList;

    public void setConditionKey(String conditionKey) {
        this.conditionKey = conditionKey;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public void setRangeList(String rangeList) {
        this.rangeList = rangeList;
    }

    public RangeConditionTemplate(String conditionKey, String op, String rangeList, String linkOp) {
        this.conditionKey = conditionKey;
        this.op = op;
        this.rangeList = rangeList;
        this.setLinkOp(linkOp);
    }

    @Override
    public String getReturnRule(String alias) {
        StringBuffer condition = new StringBuffer(conditionKey + " " + op + " ("+rangeList+")");
        return condition.toString();
    }
}
