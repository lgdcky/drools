package com.server.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/19/18
 * Time: 10:14 AM
 */
public class RuleGroupRef implements Serializable {

    private Long id;
    private Long rule_id;
    private Long ruleGroup_id;
    private Integer orderNo;
    private Integer state;
    private List<RuleHead> ruleHeadList;

    public List<RuleHead> getRuleHeadList() {
        return ruleHeadList;
    }

    public void setRuleHeadList(List<RuleHead> ruleHeadList) {
        this.ruleHeadList = ruleHeadList;
    }

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

    public Long getRuleGroup_id() {
        return ruleGroup_id;
    }

    public void setRuleGroup_id(Long ruleGroup_id) {
        this.ruleGroup_id = ruleGroup_id;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    private Date create_time;
}
