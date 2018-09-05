package com.server.command;

import com.server.model.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/22/18
 * Time: 2:31 PM
 */
public class RuleCommand implements Serializable {

    private Long id;

    private String ruleName;

    private String packageName;

    private Date create_Time;

    private String remarks;

    private Integer state;

    private String ruleString;

    private List<RuleGlobal> ruleGlobals;

    private List<RuleInfo> ruleInfos;

    private List<RuleGroup> ruleGroup;

    private List<RuleOp> ruleOps;

    public List<RuleOp> getRuleOps() {
        return ruleOps;
    }

    public void setRuleOps(List<RuleOp> ruleOps) {
        this.ruleOps = ruleOps;
    }

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRuleString() {
        return ruleString;
    }

    public void setRuleString(String ruleString) {
        this.ruleString = ruleString;
    }

    public List<RuleGlobal> getRuleGlobals() {
        return ruleGlobals;
    }

    public void setRuleGlobals(List<RuleGlobal> ruleGlobals) {
        this.ruleGlobals = ruleGlobals;
    }

    public List<RuleInfo> getRuleInfos() {
        return ruleInfos;
    }

    public void setRuleInfos(List<RuleInfo> ruleInfos) {
        this.ruleInfos = ruleInfos;
    }

    public List<RuleGroup> getRuleGroup() {
        return ruleGroup;
    }

    public void setRuleGroup(List<RuleGroup> ruleGroup) {
        this.ruleGroup = ruleGroup;
    }
}
