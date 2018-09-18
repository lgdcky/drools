package com.server.manager.rule;

import com.server.MessageCommand.RuleMessage;
import com.server.command.RuleCommand;
import com.server.model.RuleGroup;
import com.server.model.RuleHead;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 6:47 PM
 */
public interface RuleOperationManager {


    public RuleMessage createRule(List<RuleCommand> ruleCommands);

    public RuleMessage updateRule(List<RuleCommand> ruleCommands);

    public RuleMessage copyRule(List<RuleCommand> ruleCommands);

    public RuleMessage deleteRuleByHead(RuleHead ruleHead);

    public RuleMessage deleteRuleByGroup(RuleGroup ruleGroup);

    public RuleMessage deleteRuleById(List<Long> ids);

    public RuleMessage checkRule(List<RuleCommand> ruleCommands);

    public RuleMessage createRuleGroup(RuleGroup ruleGroup);

    public RuleMessage deleteRuleGroup(RuleGroup ruleGroupInfo);

    public RuleMessage updateRuleGroup(RuleGroup ruleGroup);

    public RuleMessage findRuleGroup(RuleGroup ruleGroup);

    public RuleMessage findRuleHead(RuleHead ruleHead);

    public RuleMessage findRuleByRuleGroup(RuleGroup ruleGroup);

}
