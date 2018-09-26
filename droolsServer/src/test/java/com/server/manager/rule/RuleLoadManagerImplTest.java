package com.server.manager.rule;

import com.server.command.RuleCommand;
import com.server.model.*;
import com.server.utility.TestNgBase;
import com.server.utility.template.*;
import com.server.utility.template.condition.BaseConditionTemplate;
import com.server.utility.template.condition.EqualConditionTemplate;
import com.server.utility.template.condition.RangeConditionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/26/18
 * Time: 10:09 AM
 */
public class RuleLoadManagerImplTest extends TestNgBase {

    @Autowired
    private RuleLoadManager ruleLoadManager;

    public static final String PACKAGENAME = "com.server.project.wms4";

    @Test
    public void testCreateRuleGroup() {

        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setGroupCode("testCode");
        ruleGroup.setCreate_time(new Date());
        ruleGroup.setGroupName("test");
        ruleGroup.setDescription("测试组");

        ruleLoadManager.createRuleGroup(ruleGroup);

        assertNotEquals(ruleGroup.getId(), null);

    }

    @Test
    public void testDeleteRuleGroup() {

        ruleLoadManager.deleteRuleGroup(null);
    }

    @Test
    public void testCreateRule() {
        RuleHead ruleHead = createRuleCommand();
        ruleLoadManager.createRule(ruleHead);
    }

    public static RuleHead createRuleCommand() {
        RuleHead ruleHead = new RuleHead();
        ruleHead.setRuleString("");
        ruleHead.setRemarks("测试");
        ruleHead.setCreate_Time(new Date());
        ruleHead.setPackageName(PACKAGENAME);
        ruleHead.setRuleName("测试用规则");

        List<RuleGlobal> ruleGlobalList = new ArrayList<>();
        RuleGlobal ruleGlobal = new RuleGlobal();
        ruleGlobal.setCreate_time(new Date());
        ruleGlobal.setGlobalName("list");
        ruleGlobal.setGlobalType(List.class.getName());
        ruleGlobalList.add(ruleGlobal);
        ruleHead.setRuleGlobalList(ruleGlobalList);


        List<RuleInfo> ruleInfoList = new ArrayList<>();
        RuleInfo ruleInfo = new RuleInfo();
        ruleInfo.setCreate_time(new Date());
        ruleInfo.setObj("OdoCommand");
        ruleInfo.setType(SimpleRuleTemplate.TYPE);


        List<RuleCondition> ruleConditions = new ArrayList<>();
        RuleCondition ruleCondition_one = new RuleCondition();
        ruleCondition_one.setConditionKey("customerId");
        ruleCondition_one.setConditionValue("1");
        ruleCondition_one.setOp(EqualConditionTemplate.EQ);
        ruleCondition_one.setCreate_time(new Date());
        ruleCondition_one.setAssociationType(BaseConditionTemplate.AND);
        ruleCondition_one.setType(BaseConditionTemplate.EQUALTYPE);
        ruleCondition_one.setParamType(BaseConditionTemplate.CONDITIONPARAM);
        ruleConditions.add(ruleCondition_one);

        RuleCondition ruleCondition_two = new RuleCondition();
        ruleCondition_two.setConditionKey("odoIndex");
        ruleCondition_two.setConditionValue("\"0\"");
        ruleCondition_two.setOp(EqualConditionTemplate.EQ);
        ruleCondition_two.setCreate_time(new Date());
        ruleCondition_two.setAssociationType(BaseConditionTemplate.OR);
        ruleCondition_two.setType(BaseConditionTemplate.EQUALTYPE);
        ruleCondition_two.setParamType(BaseConditionTemplate.CONDITIONPARAM);
        ruleConditions.add(ruleCondition_two);

        RuleCondition ruleCondition_thr = new RuleCondition();
        ruleCondition_thr.setConditionKey("odoType");
        ruleCondition_thr.setConditionValue("\"1\",\"2\",\"3\",\"4\"");
        ruleCondition_thr.setOp(RangeConditionTemplate.IN);
        ruleCondition_thr.setCreate_time(new Date());
        ruleCondition_thr.setAssociationType(BaseConditionTemplate.AND);
        ruleCondition_thr.setType(BaseConditionTemplate.RANGETYPE);
        ruleCondition_thr.setParamType(BaseConditionTemplate.CONDITIONPARAM);
        ruleConditions.add(ruleCondition_thr);

        ruleInfo.setRuleConditions(ruleConditions);


        RuleInfo ruleInfo_one = new RuleInfo();
        ruleInfo_one.setCreate_time(new Date());
        ruleInfo_one.setObj("WhOdoLineCommand");
        ruleInfo_one.setParent("OdoCommand");
        ruleInfo_one.setType(NestingExistsTemplate.TYPE);
        ruleInfo_one.setAttribute("whOdoLineCommands");


        List<RuleCondition> ruleConditionn_one = new ArrayList<>();
        RuleCondition RuleConditionn_one = new RuleCondition();
        RuleConditionn_one.setConditionKey("skuBarCode");
        RuleConditionn_one.setConditionValue("\"192283108137\"");
        RuleConditionn_one.setOp(EqualConditionTemplate.EQ);
        RuleConditionn_one.setCreate_time(new Date());
        RuleConditionn_one.setAssociationType(BaseConditionTemplate.AND);
        RuleConditionn_one.setType(BaseConditionTemplate.EQUALTYPE);
        RuleConditionn_one.setParamType(BaseConditionTemplate.CONDITIONPARAM);
        ruleConditionn_one.add(RuleConditionn_one);
        ruleInfo_one.setRuleConditions(ruleConditionn_one);


        RuleInfo ruleInfo_two = new RuleInfo();
        ruleInfo_two.setCreate_time(new Date());
        ruleInfo_two.setObj("WhOdoLineCommand");
        ruleInfo_two.setParent("OdoCommand");
        ruleInfo_two.setType(NestingCountTemplate.TYPE);
        ruleInfo_two.setAttribute("whOdoLineCommands");
        ruleInfo_two.setCalculation("list");


        List<RuleCondition> ruleConditionnc_one = new ArrayList<>();
        RuleCondition RuleConditionnc_one = new RuleCondition();
        RuleConditionnc_one.setConditionKey(".size()");
        RuleConditionnc_one.setConditionValue("1");
        RuleConditionnc_one.setOp(EqualConditionTemplate.GT);
        RuleConditionnc_one.setCreate_time(new Date());
        RuleConditionnc_one.setAssociationType(BaseConditionTemplate.AND);
        RuleConditionnc_one.setType(BaseConditionTemplate.EQUALTYPE);
        RuleConditionnc_one.setParamType(BaseConditionTemplate.CALPARAM);


        RuleCondition RuleConditionncc_one = new RuleCondition();
        RuleConditionncc_one.setConditionKey("skuBarCode");
        RuleConditionncc_one.setConditionValue("\"192283108137\"");
        RuleConditionncc_one.setOp(EqualConditionTemplate.EQ);
        RuleConditionncc_one.setCreate_time(new Date());
        RuleConditionncc_one.setAssociationType(BaseConditionTemplate.AND);
        RuleConditionncc_one.setType(BaseConditionTemplate.EQUALTYPE);
        RuleConditionncc_one.setParamType(BaseConditionTemplate.CONDITIONPARAM);


        ruleConditionnc_one.add(RuleConditionnc_one);
        ruleConditionnc_one.add(RuleConditionncc_one);
        ruleInfo_two.setRuleConditions(ruleConditionnc_one);


        RuleInfo ruleInfo_thr = new RuleInfo();
        ruleInfo_thr.setCreate_time(new Date());
        ruleInfo_thr.setObj("WhOdoLineCommand");
        ruleInfo_thr.setParent("OdoCommand");
        ruleInfo_thr.setType(NestingCalculationTemplate.TYPE);
        ruleInfo_thr.setAttribute("whOdoLineCommands");
        ruleInfo_thr.setCalculation("Qty");

        List<RuleCondition> ruleConditionncc_one = new ArrayList<>();
        RuleCondition RuleConditionnccs_one = new RuleCondition();
        RuleConditionnccs_one.setConditionKey("skuBarCode");
        RuleConditionnccs_one.setConditionValue("\"192283108137\"");
        RuleConditionnccs_one.setOp(EqualConditionTemplate.EQ);
        RuleConditionnccs_one.setCreate_time(new Date());
        RuleConditionnccs_one.setAssociationType(BaseConditionTemplate.AND);
        RuleConditionnccs_one.setType(BaseConditionTemplate.EQUALTYPE);
        RuleConditionnccs_one.setParamType(BaseConditionTemplate.CONDITIONPARAM);
        ruleConditionncc_one.add(RuleConditionnccs_one);
        ruleInfo_thr.setRuleConditions(ruleConditionncc_one);

        RuleCondition RuleConditionnccss_one = new RuleCondition();
        RuleConditionnccss_one.setConditionKey("");
        RuleConditionnccss_one.setConditionValue("5");
        RuleConditionnccss_one.setOp(EqualConditionTemplate.GT);
        RuleConditionnccss_one.setCreate_time(new Date());
        RuleConditionnccss_one.setAssociationType(BaseConditionTemplate.AND);
        RuleConditionnccss_one.setType(BaseConditionTemplate.EQUALTYPE);
        RuleConditionnccss_one.setParamType(BaseConditionTemplate.CALPARAM);
        ruleConditionncc_one.add(RuleConditionnccss_one);
        ruleInfo_thr.setRuleConditions(ruleConditionncc_one);

        ruleInfoList.add(ruleInfo);
        ruleInfoList.add(ruleInfo_one);
        ruleInfoList.add(ruleInfo_two);
        ruleInfoList.add(ruleInfo_thr);
        ruleHead.setRuleInfoList(ruleInfoList);

        List<RuleOp> ruleOps = new ArrayList<>();
        RuleOp ruleOp = new RuleOp();
        ruleOp.setAttribute("message");
        ruleOp.setCreate_time(new Date());
        ruleOp.setObjName("OdoCommand");
        ruleOp.setType(BaseOperationTemplate.SIMPLEOPERATIONTYPE);
        ruleOp.setValue("\"111111\"");
        ruleOps.add(ruleOp);

        ruleHead.setRuleOpList(ruleOps);
        return ruleHead;
    }

    @Test
    public void testCreateRuleRef() {
        RuleGroupRef ruleGroupRef = new RuleGroupRef();
        ruleGroupRef.setRuleGroup_id(13l);
        ruleGroupRef.setRule_id(2l);
        ruleGroupRef.setCreate_time(new Date());
        ruleGroupRef.setOrderNo(1);
        ruleGroupRef.setState(1);
        ruleLoadManager.createRuleRef(ruleGroupRef);
    }

    @Test
    public void testUpdateRuleRef() {
        RuleGroupRef ruleGroupRef = new RuleGroupRef();
        ruleGroupRef.setId(4l);
        ruleGroupRef.setRuleGroup_id(13l);
        ruleGroupRef.setRule_id(1l);
        ruleGroupRef.setCreate_time(new Date());
        ruleGroupRef.setOrderNo(2);
        ruleGroupRef.setState(1);
        ruleLoadManager.updateRuleRef(ruleGroupRef);
    }

    @Test
    public void testDeleteRuleRef() {
        ruleLoadManager.deleteRuleRef(null);
    }

    @Test
    public void testUpdateRuleGroup() {
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setId(13l);
        ruleGroup.setGroupCode("testCode");
        ruleGroup.setCreate_time(new Date());
        ruleGroup.setGroupName("test");
        ruleGroup.setDescription("测试组说明");

        ruleLoadManager.updateRuleGroup(ruleGroup);
    }

    @Test
    public void testDeleteRule() {
        RuleHead ruleHead = new RuleHead();
        ruleHead.setId(1l);
        ruleLoadManager.deleteRule(1l,13l);
    }

    @Test
    public void testUpdateRule() {
        RuleHead ruleHead = createRuleCommand();
        ruleHead.setRemarks("测试");
        ruleHead.setId(2l);
        ruleLoadManager.updateRule(ruleHead);
    }

    @Test
    public void testLoadAllRules() {
        assertEquals(ruleLoadManager.loadAllRules().size(),1);
    }

    @Test
    public void testLoadRule() {
        assertEquals(ruleLoadManager.loadRule("testCode").size(),1);
    }
}