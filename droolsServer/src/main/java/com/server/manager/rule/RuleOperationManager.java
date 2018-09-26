package com.server.manager.rule;

import com.server.MessageCommand.RuleMessage;
import com.server.command.RuleCommand;
import com.server.model.RuleGroup;
import com.server.model.RuleGroupRef;
import com.server.model.RuleHead;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 6:47 PM
 */
public interface RuleOperationManager {


    public RuleMessage createRule(RuleHead ruleHead);

    public RuleMessage updateRule(RuleHead ruleHead);

    public RuleMessage copyRule(RuleHead ruleHead);

    public RuleMessage deleteRuleRefByGroup(RuleGroup ruleGroup);

    public RuleMessage deleteRuleById(List<Long> ids);

    public RuleMessage checkRule(List<RuleCommand> ruleCommands);

    public RuleMessage createRuleGroup(RuleGroup ruleGroup);

    public RuleMessage deleteRuleGroup(RuleGroup ruleGroupInfo);

    public RuleMessage updateRuleGroup(RuleGroup ruleGroup);

    public RuleMessage findRuleGroupWithPage(RuleGroup ruleGroup,Integer pageNum,Integer size);

    public RuleMessage findRuleByRuleGroup(RuleGroup ruleGroup);

    public RuleMessage findRuleHeadWithPage(RuleHead ruleHead,Integer pageNum,Integer size);

    public RuleMessage findRuleByRuleGroupWithPage(RuleGroup ruleGroup,Integer pageNum,Integer size);

    public RuleMessage deleteRuleGroupRef(RuleGroupRef ruleGroupRef);

    public RuleMessage updateRuleGroupRef(RuleGroupRef ruleGroupRef);

}
