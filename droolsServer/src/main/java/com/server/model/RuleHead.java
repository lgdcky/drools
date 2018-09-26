package com.server.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/21/18
 * Time: 1:51 PM
 */
public class RuleHead implements Serializable {

    private Long id;

    private String ruleName;

    private String packageName;

    private Date create_Time;

    private String remarks;

    private String ruleString;

    private List<RuleInfo> ruleInfoList;

    private List<RuleGlobal> ruleGlobalList;

    private List<RuleOp> ruleOpList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Date getCreate_Time() {
        return create_Time;
    }

    public void setCreate_Time(Date create_Time) {
        this.create_Time = create_Time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRuleString() {
        return ruleString;
    }

    public void setRuleString(String ruleString) {
        this.ruleString = ruleString;
    }

    public List<RuleInfo> getRuleInfoList() {
        return ruleInfoList;
    }

    public void setRuleInfoList(List<RuleInfo> ruleInfoList) {
        this.ruleInfoList = ruleInfoList;
    }

    public List<RuleOp> getRuleOpList() {
        return ruleOpList;
    }

    public void setRuleOpList(List<RuleOp> ruleOpList) {
        this.ruleOpList = ruleOpList;
    }

    public List<RuleGlobal> getRuleGlobalList() {
        return ruleGlobalList;
    }

    public void setRuleGlobalList(List<RuleGlobal> ruleGlobalList) {
        this.ruleGlobalList = ruleGlobalList;
    }
}
