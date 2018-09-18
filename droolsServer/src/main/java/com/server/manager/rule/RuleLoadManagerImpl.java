package com.server.manager.rule;

import com.server.command.RuleCommand;
import com.server.dao.*;
import com.server.model.*;
import com.server.utility.template.*;
import com.server.utility.template.condition.*;
import org.drools.compiler.lang.api.CEDescrBuilder;
import org.drools.compiler.lang.api.RuleDescrBuilder;
import org.drools.compiler.lang.descr.AndDescr;
import org.drools.core.io.impl.BaseResource;
import org.drools.core.io.impl.ByteArrayResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public void saveRule(RuleCommand ruleCommand) {
        RuleHead ruleHead = new RuleHead();
        ruleHead.setState(ruleCommand.getState());
        ruleHead.setRuleString(ruleCommand.getRuleString());
        ruleHead.setRemarks(ruleCommand.getRemarks());
        ruleHead.setCreate_Time(ruleCommand.getCreate_Time());
        ruleHead.setRuleName(ruleCommand.getRuleName());
        ruleHead.setPackageName(ruleCommand.getPackageName());
        ruleHeadDao.saveRuleHead(ruleHead);
        Long rule_id = ruleHead.getId();
        ruleCommand.getRuleGlobals().forEach(global -> {
            global.setRule_id(rule_id);
            ruleGlobalDao.saveRuleGlobal(global);
        });

        ruleCommand.getRuleGroup().forEach(ruleGroup -> {
            ruleGroup.setRule_id(rule_id);
            ruleGroupDao.saveRuleGroup(ruleGroup);
        });

        ruleCommand.getRuleInfos().forEach(ruleInfo -> {
            ruleInfo.setRule_id(rule_id);
            ruleInfoDao.saveRuleInfo(ruleInfo);
            ruleInfo.getRuleConditions().forEach(ruleCondition -> {
                ruleCondition.setRuleInfo_id(ruleInfo.getId());
                ruleConditionDao.saveRuleCondition(ruleCondition);
            });
        });

        ruleCommand.getRuleOps().forEach(ruleOp -> {
            ruleOp.setRule_id(rule_id);
            ruleOpDao.saveRuleOp(ruleOp);
        });
    }

    @Override
    public void deleteRule(Long ruleId) {
        RuleHead ruleHead = new RuleHead();
        ruleHead.setId(ruleId);
        ruleHeadDao.deleteRuleHeadByParameter(ruleHead);

        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setRule_id(ruleId);
        ruleGroupDao.deleteRuleGroupByParameter(ruleGroup);

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
    public void updateRule(RuleCommand ruleCommand) {
        Long rule_id = ruleCommand.getId();
        RuleHead ruleHead = new RuleHead();
        ruleHead.setId(rule_id);
        ruleHead.setState(ruleCommand.getState());
        ruleHead.setRuleString(ruleCommand.getRuleString());
        ruleHead.setRemarks(ruleCommand.getRemarks());
        ruleHead.setCreate_Time(ruleCommand.getCreate_Time());
        ruleHead.setRuleName(ruleCommand.getRuleName());
        ruleHead.setPackageName(ruleCommand.getPackageName());
        ruleHeadDao.updateRuleHeadByParameter(ruleHead);

        ruleCommand.getRuleGlobals().forEach(global -> {
            global.setRule_id(rule_id);
            ruleGlobalDao.updateRuleGlobalByParameter(global);
        });

        ruleCommand.getRuleGroup().forEach(ruleGroup -> {
            ruleGroup.setRule_id(rule_id);
            ruleGroupDao.updateRuleGroupByParameter(ruleGroup);
        });

        RuleInfo ruleInfo = new RuleInfo();
        ruleInfo.setRule_id(rule_id);
        List<RuleInfo> ruleInfos = ruleInfoDao.findRuleInfoByParameter(ruleInfo);
        ruleInfoDao.deleteRuleInfoByParameter(ruleInfo);
        ruleInfos.forEach(ruleInfo_d -> {
            RuleCondition ruleCondition = new RuleCondition();
            ruleCondition.setRuleInfo_id(ruleInfo_d.getId());
            ruleConditionDao.deleteRuleConditionByParameter(ruleCondition);
        });

        ruleCommand.getRuleInfos().forEach(ruleInfo_u -> {
            ruleInfo_u.setRule_id(rule_id);
            ruleInfoDao.saveRuleInfo(ruleInfo_u);
            ruleInfo_u.getRuleConditions().forEach(ruleCondition -> {
                ruleCondition.setRuleInfo_id(ruleInfo_u.getId());
                ruleConditionDao.saveRuleCondition(ruleCondition);
            });
        });

        RuleOp ruleOp = new RuleOp();
        ruleOp.setRule_id(rule_id);
        List<RuleOp> ruleOpList = ruleOpDao.findRuleOpByParameter(ruleOp);
        ruleOpDao.deleteRuleOpByParameter(ruleOp);

        ruleCommand.getRuleOps().forEach(ruleOp_u -> {
            ruleOp_u.setRule_id(rule_id);
            ruleOpDao.saveRuleOp(ruleOp_u);
        });
    }

    @Override
    public void updateRuleGroup(RuleGroup ruleGroup) {
        ruleGroupDao.updateRuleGroupByParameter(ruleGroup);
    }

    @Override
    public Long deleteRuleGroup(RuleGroup ruleGroup) {
        return ruleGroupDao.deleteRuleGroupByParameter(ruleGroup);
    }

    @Override
    public void saveRuleGroup(RuleGroup ruleGroup) {
        ruleGroupDao.saveRuleGroup(ruleGroup);
    }

    @Override
    public List<RuleGroup> findRuleGroup(RuleGroup ruleGroup) {
        return ruleGroupDao.findRuleGroupByParameter(ruleGroup);
    }

    @Override
    public List<RuleInfo> findRuleInfo(RuleInfo ruleInfo) {
        return ruleInfoDao.findRuleInfoByParameter(ruleInfo);
    }

    @Override
    public void deleteRuleInfo(RuleInfo ruleInfo) {
        List<RuleInfo> ruleInfos = ruleInfoDao.findRuleInfoByParameter(ruleInfo);
        ruleInfoDao.deleteRuleInfoByParameter(ruleInfo);
        ruleInfos.forEach(ruleInfo_d -> {
            RuleCondition ruleCondition = new RuleCondition();
            ruleCondition.setRuleInfo_id(ruleInfo_d.getId());
            ruleConditionDao.deleteRuleConditionByParameter(ruleCondition);
        });
    }

    @Override
    public void saveRuleInfo(RuleInfo ruleInfo) {
        ruleInfoDao.saveRuleInfo(ruleInfo);
        ruleInfo.getRuleConditions().forEach(ruleCondition -> {
            ruleCondition.setRuleInfo_id(ruleInfo.getId());
            ruleConditionDao.saveRuleCondition(ruleCondition);
        });
    }

    @Override
    public void updateRuleHead(RuleHead ruleHead) {
        ruleHeadDao.updateRuleHeadByParameter(ruleHead);
    }

    @Override
    public List<RuleHead> findAllRuleHead() {
        return ruleHeadDao.findAllRuleHeads();
    }

    @Override
    public List<RuleHead> findRuleHead(RuleHead ruleHead) {
        return ruleHeadDao.findRuleHead(ruleHead);
    }

    @Override
    public void updateRuleOp(RuleOp ruleOp) {
        ruleOpDao.updateRuleOpByParameter(ruleOp);
    }

    @Override
    public void deleteRuleOp(RuleOp ruleOp) {
        ruleOpDao.deleteRuleOpByParameter(ruleOp);
    }

    @Override
    public void saveQuery() {

    }

    @Override
    public void deleteQuery() {

    }
}
