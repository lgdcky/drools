package com.server.manager.rule;

import com.server.command.RuleCommand;
import com.server.model.RuleGroup;
import com.server.model.RuleHead;
import com.server.model.RuleInfo;
import com.server.model.RuleOp;
import org.drools.core.io.impl.BaseResource;
import org.kie.api.io.Resource;

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

    public void saveRule(RuleCommand ruleCommand);

    public void deleteRule(Long ruleId);

    public void updateRule(RuleCommand ruleCommand);

    public void updateRuleGroup(RuleGroup ruleGroup);

    public void deleteRuleGroup(RuleGroup ruleGroup);

    public void saveRuleGroup(RuleGroup ruleGroup);

    public List<RuleGroup> findRuleGroup(RuleGroup ruleGroup);

    public List<RuleInfo> findRuleInfo(RuleInfo ruleInfo);

    public void deleteRuleInfo(RuleInfo ruleInfo);

    public void saveRuleInfo(RuleInfo ruleInfo);

    public void updateRuleHead(RuleHead ruleHead);

    public List<RuleHead> findAllRuleHead();

    public RuleHead findRuleHead(RuleHead ruleHead);

    public void updateRuleOp(RuleOp ruleOp);

    public void deleteRuleOp(RuleOp ruleOp);

    public void saveQuery();

    public void deleteQuery();

}
