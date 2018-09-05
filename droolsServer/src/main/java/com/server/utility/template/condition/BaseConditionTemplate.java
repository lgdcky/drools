package com.server.utility.template.condition;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/15/18
 * Time: 3:33 PM
 */
public abstract class BaseConditionTemplate<T> {

    public static final String CALPARAM = "calparam";

    public static final String CONDITIONPARAM = "conditionparam";

    public static final String CONTAINSTYPE = "contains";

    public static final String EQUALTYPE = "equal";

    public static final String MEMBEROFTYPE = "memberOf";

    public static final String RANGETYPE = "range";

    public static final String OR = "||";

    public static final String AND = "";

    private String linkOp;

    private String paramType;

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getLinkOp() {
        return linkOp;
    }

    public void setLinkOp(String linkOp) {
        this.linkOp = linkOp;
    }

    public abstract String getReturnRule(String alias);

}
