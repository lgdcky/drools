package com.server.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/21/18
 * Time: 6:49 PM
 */
public class RuleInfo implements Serializable {

    private Long id;
    private Long rule_id;
    private String type;
    private String parent;
    private String obj;
    private String calculation;
    private String attribute;
    private Date create_time;
    private List<RuleCondition> ruleConditions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRule_id() {
        return rule_id;
    }

    public void setRule_id(Long rule_id) {
        this.rule_id = rule_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public List<RuleCondition> getRuleConditions() {
        return ruleConditions;
    }

    public void setRuleConditions(List<RuleCondition> ruleConditions) {
        this.ruleConditions = ruleConditions;
    }
}
