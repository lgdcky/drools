package com.server.tools;

import com.server.command.RuleCommand;
import com.server.model.RuleGroup;
import com.server.model.RuleGroupRef;
import com.server.model.RuleHead;
import com.server.model.RuleInfo;
import com.server.utility.template.*;
import com.server.utility.template.condition.*;
import org.drools.compiler.lang.api.CEDescrBuilder;
import org.drools.compiler.lang.api.RuleDescrBuilder;
import org.drools.compiler.lang.descr.AndDescr;
import org.drools.compiler.lang.descr.PatternDescr;
import org.drools.core.io.impl.BaseResource;
import org.drools.core.io.impl.ByteArrayResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/18/18
 * Time: 9:52 AM
 * <p>
 * 用于将当前数据结构转换为Resource
 */
public final class DroolsConvertToResource {

    public static Map<String, List<BaseResource>> getResourceMap(List<RuleCommand> ruleCommands) {
        Map<String, List<BaseResource>> map = new HashMap<>();
        for (RuleCommand ruleCommand : ruleCommands) {
            configRule(map, ruleCommand);
        }

        return map;
    }

    public static void configRule(Map<String, List<BaseResource>> map, RuleCommand ruleCommand) {
        ruleCommand.getRuleGroupRefList().forEach(ruleGroupRef -> {
            configRuleHead(map, ruleCommand, ruleGroupRef);
        });
    }

    public static void configRuleHead(Map<String, List<BaseResource>> map, RuleCommand ruleCommand, RuleGroupRef ruleGroupRef) {
        ruleGroupRef.getRuleHeadList().forEach(ruleHead -> {
            ByteArrayResource byteArrayResource = null;
            if (null != ruleHead.getRuleString()) {
                CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder = configRuleHeaderTemplate(ruleCommand, ruleGroupRef, ruleHead);
                configRuleInfo(ruleHead, ceDescrBuilder);
                byteArrayResource = configRuleEndTemplate(ruleHead, ceDescrBuilder);
            } else {
                byteArrayResource = configRuleWithRuleString(ruleHead);
            }
            configResourceMap(map, byteArrayResource, ruleCommand.getGroupCode());
        });
    }

    public static ByteArrayResource configRuleWithRuleString(RuleHead ruleHead) {
        ByteArrayResource byteArrayResource;
        StringTemplate stringTemplate = new StringTemplate();
        byteArrayResource = stringTemplate.setStringTemplate(ruleHead.getRuleString());
        return byteArrayResource;
    }

    public static ByteArrayResource configRuleEndTemplate(RuleHead ruleHead, CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder) {
        ByteArrayResource byteArrayResource;
        StringBuffer op = configRuleOp(ruleHead);
        EndTemplate endTemplate = new EndTemplate(ceDescrBuilder, op.toString());
        byteArrayResource = endTemplate.returnRuleString();
        return byteArrayResource;
    }

    public static CEDescrBuilder<RuleDescrBuilder, AndDescr> configRuleHeaderTemplate(RuleCommand ruleCommand, RuleGroupRef ruleGroupRef, RuleHead ruleHead) {
        Map<String, String> ruleGlobals = configRuleGlobal(ruleHead);
        HanderTemplate handerTemplate = new HanderTemplate(ruleHead.getPackageName(), ruleHead.getRuleName(), ruleGlobals, ruleCommand.getGroupCode(), String.valueOf(ruleGroupRef.getOrderNo()));
        return handerTemplate.getCeDescrBuilder();
    }

    public static StringBuffer configRuleOp(RuleHead ruleHead) {
        StringBuffer op = new StringBuffer();
        ruleHead.getRuleOpList().forEach(ruleOp -> {
            if (BaseOperationTemplate.SIMPLEOPERATIONTYPE.equals(ruleOp.getType())) {
                SimpleOperationTemplate simpleOperationTemplate = new SimpleOperationTemplate();
                op.append(simpleOperationTemplate.opTemplate(ruleOp.getObjName(), ruleOp.getAttribute(), ruleOp.getValue()));
            }
            if (BaseOperationTemplate.STRINGYPE.equals(ruleOp.getType())) {
                StringOperationTemplate stringOperationTemplate = new StringOperationTemplate();
                op.append(stringOperationTemplate.opTemplate(null, null, ruleOp.getValue()));
            }
        });
        return op;
    }

    public static void configRuleInfo(RuleHead ruleHead, CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder) {
        List<RuleInfo> ruleInfos = ruleHead.getRuleInfoList();
        for (RuleInfo ruleInfo : ruleInfos) {
            configRuleInfo(ceDescrBuilder, ruleInfo);
        }
    }

    public static Map<String, String> configRuleGlobal(RuleHead ruleHead) {
        Map<String, String> ruleGlobals = new HashMap<>();
        ruleHead.getRuleGlobalList().forEach(ruleGlobal -> {
            ruleGlobals.put(ruleGlobal.getGlobalName(), ruleGlobal.getGlobalType());
        });
        return ruleGlobals;
    }

    public static void configResourceMap(Map<String, List<BaseResource>> map, ByteArrayResource byteArrayResource, String ruleGroupCode) {
        List<BaseResource> list = map.get(ruleGroupCode);
        if (null == list) {
            list = new ArrayList<>();
            list.add(byteArrayResource);
            map.put(ruleGroupCode, list);
        } else {
            list.add(byteArrayResource);
        }
    }


    public static void configRuleInfo(CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder, RuleInfo ruleInfo) {
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

    public static void configNestingCalculationTemplate(CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder, RuleInfo ruleInfo, List<BaseConditionTemplate> conditionList) {
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

    public static void configNestingExistsTemplate(CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder, RuleInfo ruleInfo, List<BaseConditionTemplate> conditionList) {
        NestingExistsTemplate nestingExistsTemplate = new NestingExistsTemplate();
        nestingExistsTemplate.setObjName(ruleInfo.getObj());
        nestingExistsTemplate.setAttribute(ruleInfo.getAttribute());
        nestingExistsTemplate.setConditionList(conditionListFilter(conditionList, BaseConditionTemplate.CONDITIONPARAM));
        nestingExistsTemplate.setParentName(ruleInfo.getParent());
        nestingExistsTemplate.setDescrBuilder(ceDescrBuilder);
        nestingExistsTemplate.setNestingExistsTemplate();
    }

    public static void configNestingCountTemplate(CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder, RuleInfo ruleInfo, List<BaseConditionTemplate> conditionList) {
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

    public static void configSimpleRuleTemplate(CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder, RuleInfo ruleInfo, List<BaseConditionTemplate> conditionList) {
        SimpleRuleTemplate simpleRuleTemplate = new SimpleRuleTemplate();
        simpleRuleTemplate.setObjName(ruleInfo.getObj());
        simpleRuleTemplate.setConditionList(conditionListFilter(conditionList, BaseConditionTemplate.CONDITIONPARAM));
        simpleRuleTemplate.setDescrBuilder(ceDescrBuilder);
        simpleRuleTemplate.setSimpleRuleTemplate();
    }

    public static List<BaseConditionTemplate> conditionListFilter(List<BaseConditionTemplate> baseConditionTemplates, String filter) {
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

    public static boolean checkRuleId(CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder, String objName) {
        boolean flag = true;
        List<PatternDescr> patternDescrList = ceDescrBuilder.getDescr().getAllPatternDescr();
        if (patternDescrList.size() == 0) {
            return flag;
        }
        for (PatternDescr patternDescr : patternDescrList) {
            if (objName.equals(patternDescr.getIdentifier())) {
                flag = false;
            }
        }
        return flag;
    }
}
