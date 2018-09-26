package com.server.manager.rule;

import com.server.command.RuleCommand;
import com.server.model.*;
import org.drools.core.io.impl.BaseResource;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/26/18
 * Time: 2:58 PM
 */
public interface RuleLoadManager {

    public Map<String,List<BaseResource>> loadAllRules();

    public List<BaseResource> loadRule(String knowledgeBaseName);

    public void createRule(RuleHead ruleHead);

    public void updateRule(RuleHead ruleHead);

    public void deleteRule(Long ruleId,Long ruleGroupId);

    public void createRuleGroup(RuleGroup ruleGroup);

    public void updateRuleGroup(RuleGroup ruleGroup);

    public void deleteRuleGroup(RuleGroup ruleGroup);

    public void createRuleRef(RuleGroupRef ruleGroupRef);

    public void updateRuleRef(RuleGroupRef ruleGroupRef);

    public void deleteRuleRef(RuleGroupRef ruleGroupRef);


}
