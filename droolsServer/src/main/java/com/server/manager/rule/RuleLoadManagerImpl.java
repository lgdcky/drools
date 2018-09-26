package com.server.manager.rule;

import com.server.command.RuleCommand;
import com.server.dao.*;
import com.server.model.*;
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
 * Date: 7/26/18
 * Time: 3:00 PM
 */
@Component
@Transactional
public class RuleLoadManagerImpl implements RuleLoadManager {

    @Autowired
    private RuleHeadDao ruleHeadDao;

    @Autowired
    private RuleGroupDao ruleGroupDao;

    @Autowired
    private RuleInfoDao ruleInfoDao;

    @Autowired
    private RuleConditionDao ruleConditionDao;

    @Autowired
    private RuleGlobalDao ruleGlobalDao;

    @Autowired
    private RuleDao ruleDao;

    @Autowired
    private RuleOpDao ruleOpDao;

    @Autowired
    private RuleGroupRefDao ruleGroupRefDao;

    @Override
    public Map<String, List<BaseResource>> loadAllRules() {
        List<RuleCommand> ruleCommands = ruleDao.findAllRule();
        return getResourceMap(ruleCommands);
    }

    @Override
    public List<BaseResource> loadRule(String knowledgeBaseName) {
        List<RuleCommand> ruleCommands = ruleDao.findAllRuleByGroup(knowledgeBaseName);
        return getResourceMap(ruleCommands).get(knowledgeBaseName);
    }

    @Override
    public void createRuleGroup(RuleGroup ruleGroup) {
        ruleGroupDao.saveRuleGroup(ruleGroup);
    }

    @Override
    public void createRule(RuleHead ruleHead) {
        ruleHeadDao.saveRuleHead(ruleHead);
        ruleHead.getRuleOpList().stream().forEach(ruleOp -> {
            ruleOp.setRule_id(ruleHead.getId());
            ruleOpDao.saveRuleOp(ruleOp);
        });

        ruleHead.getRuleGlobalList().stream().forEach(ruleGlobal -> {
            ruleGlobal.setRule_id(ruleHead.getId());
            ruleGlobalDao.saveRuleGlobal(ruleGlobal);
        });

        ruleHead.getRuleInfoList().stream().forEach(ruleInfo -> {
            ruleInfo.setRule_id(ruleHead.getId());
            ruleInfoDao.saveRuleInfo(ruleInfo);
            ruleInfo.getRuleConditions().stream().forEach(ruleCondition -> {
                ruleCondition.setRuleInfo_id(ruleInfo.getId());
                ruleConditionDao.saveRuleCondition(ruleCondition);
            });
        });
    }

    @Override
    public void deleteRule(Long ruleId, Long ruleGroupId) {
        RuleHead ruleHead = new RuleHead();
        ruleHead.setId(ruleId);
        ruleHeadDao.deleteRuleHeadByParameter(ruleHead);

        if (null != ruleGroupId) {
            RuleGroupRef ruleGroupRef = new RuleGroupRef();
            ruleGroupRef.setRule_id(ruleId);
            ruleGroupRef.setRuleGroup_id(ruleGroupId);
            ruleGroupRefDao.deleteRuleGroupRefByParameter(ruleGroupRef);
        }

        RuleGlobal ruleGlobal = new RuleGlobal();
        ruleGlobal.setRule_id(ruleId);
        ruleGlobalDao.deleteRuleGlobalByParameter(ruleGlobal);

        RuleInfo ruleInfo = new RuleInfo();
        ruleInfo.setRule_id(ruleId);

        List<RuleInfo> ruleInfos = ruleInfoDao.findRuleInfoByParameter(ruleInfo);

        ruleInfoDao.deleteRuleInfoByParameter(ruleInfo);
        ruleInfos.forEach(ruleInfo_d -> {
            RuleCondition ruleCondition = new RuleCondition();
            ruleCondition.setRuleInfo_id(ruleInfo_d.getId());
            ruleConditionDao.deleteRuleConditionByParameter(ruleCondition);
        });

        RuleOp ruleOp = new RuleOp();
        ruleOp.setRule_id(ruleId);
        ruleOpDao.deleteRuleOpByParameter(ruleOp);
    }

    @Override
    public void updateRule(RuleHead ruleHead) {
        ruleHeadDao.updateRuleHeadByParameter(ruleHead);

        ruleHead.getRuleOpList().stream().forEach(ruleOp -> {
            ruleOpDao.deleteRuleOpByParameter(ruleOp);
            ruleOp.setId(null);
            ruleOp.setRule_id(ruleHead.getId());
            ruleOpDao.saveRuleOp(ruleOp);
        });

        ruleHead.getRuleGlobalList().stream().forEach(ruleGlobal -> {
            ruleGlobalDao.deleteRuleGlobalByParameter(ruleGlobal);
            ruleGlobal.setId(null);
            ruleGlobal.setRule_id(ruleHead.getId());
            ruleGlobalDao.saveRuleGlobal(ruleGlobal);
        });

        ruleHead.getRuleInfoList().stream().forEach(ruleInfo -> {
            ruleInfoDao.deleteRuleInfoByParameter(ruleInfo);
            ruleInfo.getRuleConditions().stream().forEach(ruleCondition -> {
                ruleConditionDao.deleteRuleConditionByParameter(ruleCondition);
            });
        });

        ruleHead.getRuleInfoList().stream().forEach(ruleInfo -> {
            ruleInfo.setId(null);
            ruleInfo.setRule_id(ruleHead.getId());
            ruleInfoDao.saveRuleInfo(ruleInfo);
            ruleInfo.getRuleConditions().stream().forEach(ruleCondition -> {
                ruleCondition.setId(null);
                ruleCondition.setRuleInfo_id(ruleInfo.getId());
                ruleConditionDao.saveRuleCondition(ruleCondition);
            });
        });
    }

    @Override
    public void updateRuleGroup(RuleGroup ruleGroup) {
        ruleGroupDao.updateRuleGroupByParameter(ruleGroup);
    }

    @Override
    public void deleteRuleGroup(RuleGroup ruleGroup) {
        List<RuleGroup> ruleGroupList = ruleGroupDao.findRuleGroupByParameter(ruleGroup);

        ruleGroupList.forEach(ruleGroupInfo -> {
            RuleGroupRef ruleGroupRef = new RuleGroupRef();
            ruleGroupRef.setRuleGroup_id(ruleGroupInfo.getId());
            ruleGroupRefDao.deleteRuleGroupRefByParameter(ruleGroupRef);
            ruleGroupDao.deleteRuleGroupByParameter(ruleGroupInfo);
        });

    }

    @Override
    public void createRuleRef(RuleGroupRef ruleGroupRef) {
        ruleGroupRefDao.saveRuleGroupRef(ruleGroupRef);
    }

    @Override
    public void updateRuleRef(RuleGroupRef ruleGroupRef) {
        ruleGroupRefDao.updateRuleGroupRefByParameter(ruleGroupRef);
    }

    @Override
    public void deleteRuleRef(RuleGroupRef ruleGroupRef) {
        ruleGroupRefDao.deleteRuleGroupRefByParameter(ruleGroupRef);
    }
}
