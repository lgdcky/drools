package com.server.manager.rule;

import com.server.MessageCommand.KnowledgeMessage;
import com.server.MessageCommand.RuleMessage;
import com.server.command.RuleCommand;
import com.server.dao.RuleDao;
import com.server.dao.RuleGroupDao;
import com.server.dao.RuleHeadDao;
import com.server.manager.knowledge.KnowLedgeBaseManger;
import com.server.model.RuleGroup;
import com.server.model.RuleHead;
import org.drools.core.io.impl.BaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.server.tools.DroolsConvertToResource.getResourceMap;
import static com.server.tools.ReflectBeanUtils.objectToMap;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 7:18 PM
 */
@Component
@Transactional
public class RuleOperationManagerImpl implements RuleOperationManager {

    private static final Logger log = LoggerFactory.getLogger(RuleOperationManagerImpl.class);

    @Autowired
    private RuleLoadManager ruleLoadManager;

    @Autowired
    private KnowLedgeBaseManger knowLedgeBaseManger;

    @Autowired
    private RuleDao ruleDao;

    @Autowired
    private RuleHeadDao ruleHeadDao;

    @Autowired
    private RuleGroupDao ruleGroupDao;

    @Override
    public RuleMessage createRule(List<RuleCommand> ruleCommands) {
        try {
            ruleCommands.forEach(ruleCommand -> {
                ruleLoadManager.saveRule(ruleCommand);
            });
            return new RuleMessage("save rule success", null, true, null);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("save rule failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage updateRule(List<RuleCommand> ruleCommands) {
        try {
            ruleCommands.forEach(ruleCommand -> {
                ruleLoadManager.updateRule(ruleCommand);
            });
            return new RuleMessage("update rule success", null, true, null);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("update rule failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage copyRule(List<RuleCommand> ruleCommands) {
        return this.createRule(ruleCommands);
    }

    @Override
    public RuleMessage deleteRuleByHead(RuleHead ruleHeadInfo) {
        try {
            List<RuleHead> ruleHeadList = ruleLoadManager.findRuleHead(ruleHeadInfo);
            ruleHeadList.forEach(ruleHead -> {
                ruleLoadManager.deleteRule(ruleHead.getId());
            });
            return new RuleMessage("delete rule by head success", null, true, null);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("delete rule by head failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage deleteRuleByGroup(RuleGroup ruleGroupInfo) {
        try {
            List<RuleGroup> ruleGroups = ruleLoadManager.findRuleGroup(ruleGroupInfo);
            ruleLoadManager.deleteRuleGroup(ruleGroupInfo);
            ruleGroups.forEach(ruleGroup -> {
                ruleLoadManager.deleteRule(ruleGroup.getRule_id());
            });
            return new RuleMessage("delete rule group success", null, true, null);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("delete rule group failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage deleteRuleById(List<Long> ids) {
        try {
            ids.forEach(id -> {
                ruleLoadManager.deleteRule(id);
            });
            return new RuleMessage("delete rule by id success", null, true, null);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("delete rule by id failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage checkRule(List<RuleCommand> ruleCommands) {
        try {
            Map<String, List<BaseResource>> map = getResourceMap(ruleCommands);

            KnowledgeMessage error = null;
            for (String group : map.keySet()) {
                error = knowLedgeBaseManger.testRule(map.get(group));
                if (null != error.getMessage()) {
                    return new RuleMessage("check rule failed", error.getMessage(), false, null);
                }
            }
            return new RuleMessage("check rule pass", null, true, null);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("delete rule failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage createRuleGroup(RuleGroup ruleGroup) {
        try {
            ruleLoadManager.saveRuleGroup(ruleGroup);
            return new RuleMessage("create rulegroup pass", null, true, null);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("create rulegroup failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage deleteRuleGroup(RuleGroup ruleGroupInfo) {
        try {
            ruleLoadManager.deleteRuleGroup(ruleGroupInfo);
            return new RuleMessage("delete rule group success", null, true, null);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("delete rule group failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage updateRuleGroup(RuleGroup ruleGroup) {
        try {
            ruleLoadManager.updateRuleGroup(ruleGroup);
            return new RuleMessage("update rulegroup pass", null, true, null);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("update rulegroup failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage findRuleGroup(RuleGroup ruleGroup) {
        try {
            List<RuleGroup> ruleGroupList = ruleLoadManager.findRuleGroup(ruleGroup);
            return new RuleMessage("find rule group success", null, true, ruleGroupList);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("find rule group failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage findRuleHead(RuleHead ruleHead) {
        try {
            List<RuleHead> ruleHeadList = ruleLoadManager.findRuleHead(ruleHead);
            return new RuleMessage("find rule head success", null, true, ruleHeadList);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("find rule head failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage findRuleByRuleGroup(RuleGroup ruleGroup) {
        try {
            List<RuleCommand> ruleCommands = ruleDao.findAllRuleByGroup(ruleGroup.getGroupCode());
            return new RuleMessage("find rule by group success", null, true, ruleCommands);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("find rule by group failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage findRuleHeadWithPage(RuleHead ruleHead,Integer pageNum,Integer size) {
        try {
            List<RuleHead> ruleHeadList = ruleHeadDao.findRuleHeadWithPage(convertToMap(ruleHead,pageNum,size));
            return new RuleMessage("find rule head success", null, true, ruleHeadList);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("find rule head failed", e.toString(), false, null);
        }
    }

    private static final Map<String,Object> convertToMap(RuleHead ruleHead,Integer pageNum,Integer size) throws Exception {
        Map<String,Object> map = objectToMap(ruleHead);
        map.put("pageNum",pageNum);
        map.put("size",size);
        return map;
    }

    @Override
    public RuleMessage findRuleByRuleGroupWithPage(RuleGroup ruleGroup,Integer pageNum,Integer size) {
        try {
            List<RuleGroup> ruleGroups = ruleGroupDao.findRuleGroupByParameterWithPage(convertToMap(ruleGroup,pageNum,size));
            return new RuleMessage("find rule by group success", null, true, ruleGroups);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("find rule by group failed", e.toString(), false, null);
        }
    }

    private static final Map<String,Object> convertToMap(RuleGroup ruleGroup,Integer pageNum,Integer size) throws Exception {
        Map<String,Object> map = objectToMap(ruleGroup);
        map.put("pageNum",pageNum);
        map.put("size",size);
        return map;
    }
}
