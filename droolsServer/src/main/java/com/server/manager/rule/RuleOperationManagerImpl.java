package com.server.manager.rule;

import com.server.MessageCommand.KnowledgeMessage;
import com.server.MessageCommand.RuleMessage;
import com.server.command.RuleCommand;
import com.server.dao.RuleDao;
import com.server.knowledgebasemanager.KnowLedgeBaseManger;
import com.server.model.RuleGroup;
import com.server.model.RuleHead;
import org.drools.core.io.impl.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.server.tools.DroolsConvertToResource.getResourceMap;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 7:18 PM
 */
@Component
@Transactional
public class RuleOperationManagerImpl implements RuleOperationManager {

    @Autowired
    private RuleLoadManager ruleLoadManager;

    @Autowired
    private KnowLedgeBaseManger knowLedgeBaseManger;

    @Autowired
    private RuleDao ruleDao;

    @Override
    public RuleMessage createRule(List<RuleCommand> ruleCommands) {
        ruleCommands.forEach(ruleCommand -> {
            ruleLoadManager.saveRule(ruleCommand);
        });
        return new RuleMessage("save rule success", null, true, null);
    }

    @Override
    public RuleMessage updateRule(List<RuleCommand> ruleCommands) {
        ruleCommands.forEach(ruleCommand -> {
            ruleLoadManager.updateRule(ruleCommand);
        });
        return new RuleMessage("update rule success", null, true, null);
    }

    @Override
    public RuleMessage copyRule(List<RuleCommand> ruleCommands) {
        return this.createRule(ruleCommands);
    }

    @Override
    public RuleMessage deleteRuleByHead(RuleHead ruleHeadInfo) {
        List<RuleHead> ruleHeadList = ruleLoadManager.findRuleHead(ruleHeadInfo);
        ruleHeadList.forEach(ruleHead -> {
            ruleLoadManager.deleteRule(ruleHead.getId());
        });
        return new RuleMessage("delete success", null, true, null);
    }

    @Override
    public RuleMessage deleteRuleByGroup(RuleGroup ruleGroupInfo) {
        List<RuleGroup> ruleGroups = ruleLoadManager.findRuleGroup(ruleGroupInfo);
        ruleLoadManager.deleteRuleGroup(ruleGroupInfo);
        ruleGroups.forEach(ruleGroup -> {
            ruleLoadManager.deleteRule(ruleGroup.getRule_id());
        });
        return new RuleMessage("update success", null, true, null);
    }

    @Override
    public RuleMessage deleteRuleById(List<Long> ids) {
        ids.forEach(id -> {
            ruleLoadManager.deleteRule(id);
        });
        return new RuleMessage("update success", null, true, null);
    }

    @Override
    public RuleMessage checkRule(List<RuleCommand> ruleCommands) {
        Map<String, List<BaseResource>> map = getResourceMap(ruleCommands);

        map.forEach((group, resourceList) -> {
            KnowledgeMessage error = knowLedgeBaseManger.testRule(resourceList);
        });
        List<BaseResource> rule = ruleLoadManager.loadRule("测试一组");
        KnowledgeMessage error = knowLedgeBaseManger.testRule(rule);
        if (null != error.getMessage()) {
            return new RuleMessage("check rule failed", error.getMessage(), false, null);
        } else {
            return new RuleMessage("check rule pass", null, true, null);
        }
    }

    @Override
    public RuleMessage createRuleGroup(RuleGroup ruleGroup) {
        ruleLoadManager.saveRuleGroup(ruleGroup);
        return new RuleMessage("create rulegroup pass", null, true, null);
    }

    @Override
    public RuleMessage deleteRuleGroup(RuleGroup ruleGroupInfo) {
        ruleLoadManager.deleteRuleGroup(ruleGroupInfo);
        return new RuleMessage("update success", null, true, null);
    }

    @Override
    public RuleMessage updateRuleGroup(RuleGroup ruleGroup) {
        ruleLoadManager.updateRuleGroup(ruleGroup);
        return new RuleMessage("update rulegroup pass", null, true, null);
    }

    @Override
    public RuleMessage findRuleGroup(RuleGroup ruleGroup) {
        List<RuleGroup> ruleGroupList = ruleLoadManager.findRuleGroup(ruleGroup);
        return new RuleMessage("find rule group", null, true, ruleGroupList);
    }

    @Override
    public RuleMessage findRuleHead(RuleHead ruleHead) {
        List<RuleHead> ruleHeadList = ruleLoadManager.findRuleHead(ruleHead);
        return new RuleMessage("find rulehead", null, true, ruleHeadList);
    }

    @Override
    public RuleMessage findRuleByRuleGroup(RuleGroup ruleGroup) {
        List<RuleCommand> ruleCommands = ruleDao.findAllRuleByGroup(ruleGroup.getGroupCode());
        return new RuleMessage("find rule", null, true, ruleCommands);
    }
}
