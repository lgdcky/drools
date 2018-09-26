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

    private String groupCode;

    private String groupName;

    private Date create_time;

    private String description;

    private List<RuleGroupRef> ruleGroupRefList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RuleGroupRef> getRuleGroupRefList() {
        return ruleGroupRefList;
    }

    public void setRuleGroupRefList(List<RuleGroupRef> ruleGroupRefList) {
        this.ruleGroupRefList = ruleGroupRefList;
    }
}
