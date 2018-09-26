package com.server.manager.rule;

import com.server.MessageCommand.KnowledgeMessage;
import com.server.MessageCommand.RuleMessage;
import com.server.command.RuleCommand;
import com.server.dao.RuleDao;
import com.server.dao.RuleGroupDao;
import com.server.dao.RuleGroupRefDao;
import com.server.dao.RuleHeadDao;
import com.server.manager.knowledge.KnowLedgeBaseManger;
import com.server.model.RuleGroup;
import com.server.model.RuleGroupRef;
import com.server.model.RuleHead;
import org.drools.core.io.impl.BaseResource;
import org.drools.template.model.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private RuleGroupRefDao ruleGroupRefDao;

    @Override
    public RuleMessage createRule(RuleHead ruleHead) {
        try {
                ruleLoadManager.createRule(ruleHead);
            return new RuleMessage("save rule success", null, true, null);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("save rule failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage updateRule(RuleHead ruleHead) {
        try {
                ruleLoadManager.updateRule(ruleHead);
            return new RuleMessage("update rule success", null, true, null);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("update rule failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage copyRule(RuleHead ruleHead) {
        return this.createRule(ruleHead);
    }

    @Override
    public RuleMessage deleteRuleRefByGroup(RuleGroup ruleGroupInfo) {
        try {
            List<RuleGroup> ruleGroups = ruleGroupDao.findRuleGroupByParameter(ruleGroupInfo);
            ruleLoadManager.deleteRuleGroup(ruleGroupInfo);
            ruleGroups.forEach(ruleGroup -> {
                RuleGroupRef ruleGroupRef = new RuleGroupRef();
                ruleGroupRef.setRuleGroup_id(ruleGroup.getId());
                ruleGroupRefDao.deleteRuleGroupRefByParameter(ruleGroupRef);
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
                RuleHead ruleHead = new RuleHead();
                ruleHead.setId(id);
                ruleHeadDao.deleteRuleHeadByParameter(ruleHead);
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
            ruleLoadManager.createRuleGroup(ruleGroup);
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
            List<RuleCommand> ruleCommandList = ruleDao.findAllRuleByGroupWithPage(convertToMap(ruleGroup,pageNum,size));
            return new RuleMessage("find rule by group success", null, true, ruleCommandList);
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

    @Override
    public RuleMessage findRuleGroupWithPage(RuleGroup ruleGroup, Integer pageNum, Integer size) {
        try {
            List<RuleGroup> ruleGroupList = ruleGroupDao.findRuleGroupByParameterWithPage(convertToMap(ruleGroup,pageNum,size));
            return new RuleMessage("find rule by group success", null, true, ruleGroupList);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("find rule by group failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage deleteRuleGroupRef(RuleGroupRef ruleGroupRef) {
        try {
            ruleGroupRefDao.deleteRuleGroupRefByParameter(ruleGroupRef);
            return new RuleMessage("find rule by group success", null, true, null);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("find rule by group failed", e.toString(), false, null);
        }
    }

    @Override
    public RuleMessage updateRuleGroupRef(RuleGroupRef ruleGroupRef) {
        try {
            ruleGroupRefDao.updateRuleGroupRefByParameter(ruleGroupRef);
            return new RuleMessage("find rule by group success", null, true, null);
        } catch (Exception e) {
            log.error(e.toString());
            return new RuleMessage("find rule by group failed", e.toString(), false, null);
        }
    }
}
