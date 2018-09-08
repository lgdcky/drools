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

import java.util.*;
import java.util.stream.Collectors;

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

    public Map<String, List<BaseResource>> getResourceMap(List<RuleCommand> ruleCommands) {
        Map<String, List<BaseResource>> map = new HashMap<>();
        for (RuleCommand ruleCommand : ruleCommands) {
            ruleCommand.getRuleGroup().forEach(ruleGroup -> {
                ByteArrayResource byteArrayResource = null;
                if (null == ruleCommand.getRuleString()) {
                    Map<String, String> ruleGlobals = new HashMap<>();
                    ruleCommand.getRuleGlobals().forEach(ruleGlobal -> {
                        ruleGlobals.put(ruleGlobal.getGlobalName(), ruleGlobal.getGlobalType());
                    });

                    HanderTemplate handerTemplate = new HanderTemplate(ruleCommand.getPackageName(), ruleCommand.getRuleName(), ruleGlobals, ruleGroup.getGroupCode(), ruleGroup.getOrderNo());
                    CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder = handerTemplate.getCeDescrBuilder();

                    List<RuleInfo> ruleInfos = ruleCommand.getRuleInfos();
                    for (RuleInfo ruleInfo : ruleInfos) {
                        configRuleInfo(ceDescrBuilder, ruleInfo);
                    }
                    StringBuffer op = new StringBuffer();
                    ruleCommand.getRuleOps().forEach(ruleOp -> {
                        if (BaseOperationTemplate.SIMPLEOPERATIONTYPE.equals(ruleOp.getType())) {
                            SimpleOperationTemplate simpleOperationTemplate = new SimpleOperationTemplate();
                            op.append(simpleOperationTemplate.opTemplate(ruleOp.getObjName(), ruleOp.getAttribute(), ruleOp.getValue()));
                        }
                        if (BaseOperationTemplate.STRINGYPE.equals(ruleOp.getType())) {
                            StringOperationTemplate stringOperationTemplate = new StringOperationTemplate();
                            op.append(stringOperationTemplate.opTemplate(null, null, ruleOp.getValue()));
                        }
                    });
                    EndTemplate endTemplate = new EndTemplate(ceDescrBuilder, op.toString());
                    byteArrayResource = endTemplate.returnRuleString();
                } else {
                    StringTemplate stringTemplate = new StringTemplate();
                    byteArrayResource = stringTemplate.setStringTemplate(ruleCommand.getRuleString());
                }
                configResourceMap(map, byteArrayResource, ruleGroup);
            });

        }
        return map;
    }

    public void configResourceMap(Map<String, List<BaseResource>> map, ByteArrayResource byteArrayResource, RuleGroup ruleGroup) {
        List<BaseResource> list = map.get(ruleGroup.getGroupCode());
        if (null == list) {
            list = new ArrayList<>();
            list.add(byteArrayResource);
            map.put(ruleGroup.getGroupCode(), list);
        } else {
            list.add(byteArrayResource);
        }
    }


    public void configRuleInfo(CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder, RuleInfo ruleInfo) {
        List<BaseConditionTemplate> conditionList = buildCondition(ruleInfo);
        if (SimpleRuleTemplate.TYPE.equals(ruleInfo.getType())) {
            configSimpleRuleTemplate(ceDescrBuilder, ruleInfo, conditionList);
        }
        if (NestingCountTemplate.TYPE.equals(ruleInfo.getType())) {
            configNestingCountTemplate(ceDescrBuilder, ruleInfo, conditionList);
        }
        if (NestingExistsTemplate.TYPE.equals(ruleInfo.getType())) {
            configNestingExistsTemplate(ceDescrBuilder, ruleInfo, conditionList);
        }
        if (NestingCalculationTemplate.TYPE.equals(ruleInfo.getType())) {
            configNestingCalculationTemplate(ceDescrBuilder, ruleInfo, conditionList);
        }
    }

    public void configNestingCalculationTemplate(CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder, RuleInfo ruleInfo, List<BaseConditionTemplate> conditionList) {
        NestingCalculationTemplate nestingCalculationTemplate = new NestingCalculationTemplate(ceDescrBuilder);
        nestingCalculationTemplate.setObjName(ruleInfo.getObj());
        nestingCalculationTemplate.setAttribute(ruleInfo.getAttribute());
        nestingCalculationTemplate.setConditionList(conditionListFilter(conditionList, BaseConditionTemplate.CONDITIONPARAM));
        nestingCalculationTemplate.setCalConditionList(conditionListFilter(conditionList, BaseConditionTemplate.CALPARAM));
        nestingCalculationTemplate.setParentName(ruleInfo.getParent());
        nestingCalculationTemplate.setCalculation(ruleInfo.getCalculation());
        nestingCalculationTemplate.setDescrBuilder(ceDescrBuilder);
        nestingCalculationTemplate.setNestingCalculationTemplate();
    }

    public void configNestingExistsTemplate(CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder, RuleInfo ruleInfo, List<BaseConditionTemplate> conditionList) {
        NestingExistsTemplate nestingExistsTemplate = new NestingExistsTemplate();
        nestingExistsTemplate.setObjName(ruleInfo.getObj());
        nestingExistsTemplate.setAttribute(ruleInfo.getAttribute());
        nestingExistsTemplate.setConditionList(conditionListFilter(conditionList, BaseConditionTemplate.CONDITIONPARAM));
        nestingExistsTemplate.setParentName(ruleInfo.getParent());
        nestingExistsTemplate.setDescrBuilder(ceDescrBuilder);
        nestingExistsTemplate.setNestingExistsTemplate();
    }

    public void configNestingCountTemplate(CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder, RuleInfo ruleInfo, List<BaseConditionTemplate> conditionList) {
        NestingCountTemplate nestingCountTemplate = new NestingCountTemplate();
        nestingCountTemplate.setObjName(ruleInfo.getObj());
        nestingCountTemplate.setAttribute(ruleInfo.getAttribute());
        nestingCountTemplate.setConditionList(conditionListFilter(conditionList, BaseConditionTemplate.CONDITIONPARAM));
        nestingCountTemplate.setCalConditionList(conditionListFilter(conditionList, BaseConditionTemplate.CALPARAM));
        nestingCountTemplate.setParentName(ruleInfo.getParent());
        nestingCountTemplate.setDescrBuilder(ceDescrBuilder);
        nestingCountTemplate.setCalculation(ruleInfo.getCalculation());
        nestingCountTemplate.setNestingCountTemplate();
    }

    public void configSimpleRuleTemplate(CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder, RuleInfo ruleInfo, List<BaseConditionTemplate> conditionList) {
        SimpleRuleTemplate simpleRuleTemplate = new SimpleRuleTemplate();
        simpleRuleTemplate.setObjName(ruleInfo.getObj());
        simpleRuleTemplate.setConditionList(conditionListFilter(conditionList, BaseConditionTemplate.CONDITIONPARAM));
        simpleRuleTemplate.setDescrBuilder(ceDescrBuilder);
        simpleRuleTemplate.setSimpleRuleTemplate();
    }

    public List<BaseConditionTemplate> conditionListFilter(List<BaseConditionTemplate> baseConditionTemplates, String filter) {
        return baseConditionTemplates.stream().filter(baseConditionTemplate -> filter.equals(baseConditionTemplate.getParamType())).collect(Collectors.toList());
    }

    public static List<BaseConditionTemplate> buildCondition(RuleInfo ruleInfo) {
        List<BaseConditionTemplate> conditionList = new ArrayList<>();
        ruleInfo.getRuleConditions().forEach(ruleCondition -> {
            if (ruleCondition.getType().equals(BaseConditionTemplate.CONTAINSTYPE)) {
                ContainsConditionTemplate containsConditionTemplate = new ContainsConditionTemplate(ruleCondition.getConditionKey(),
                        ruleCondition.getOp(), ruleCondition.getConditionValue(), ruleCondition.getAssociationType());
                containsConditionTemplate.setParamType(ruleCondition.getParamType());
                conditionList.add(containsConditionTemplate);
            }
            if (ruleCondition.getType().equals(BaseConditionTemplate.EQUALTYPE)) {
                EqualConditionTemplate equalConditionTemplate = new EqualConditionTemplate(ruleCondition.getConditionKey(),
                        ruleCondition.getOp(), ruleCondition.getConditionValue(), ruleCondition.getAssociationType());
                equalConditionTemplate.setParamType(ruleCondition.getParamType());
                conditionList.add(equalConditionTemplate);
            }
            if (ruleCondition.getType().equals(BaseConditionTemplate.MEMBEROFTYPE)) {
                MemberOfConditionTemplate memberOfConditionTemplate = new MemberOfConditionTemplate(ruleCondition.getConditionKey(),
                        ruleCondition.getOp(), ruleCondition.getConditionValue(), ruleCondition.getAssociationType());
                memberOfConditionTemplate.setParamType(ruleCondition.getParamType());
                conditionList.add(memberOfConditionTemplate);
            }
            if (ruleCondition.getType().equals(BaseConditionTemplate.RANGETYPE)) {
                RangeConditionTemplate rangeConditionTemplate = new RangeConditionTemplate(ruleCondition.getConditionKey(),
                        ruleCondition.getOp(), ruleCondition.getConditionValue(), ruleCondition.getAssociationType());
                rangeConditionTemplate.setParamType(ruleCondition.getParamType());
                conditionList.add(rangeConditionTemplate);
            }
        });
        return conditionList;
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
    public void deleteRuleGroup(RuleGroup ruleGroup) {
        ruleGroupDao.deleteRuleGroupByParameter(ruleGroup);
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
    public RuleHead findRuleHead(RuleHead ruleHead) {
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
