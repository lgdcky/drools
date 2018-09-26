package com.server.QueryTest;

import com.server.MessageCommand.KnowledgeMessage;
import com.server.project.wms4.OdoCommand;
import com.server.command.RuleCommand;
import com.server.project.wms4.WhOdoLineCommand;
import com.server.manager.knowledge.KnowLedgeBaseManger;
import com.server.model.*;
import com.server.manager.query.QueryManager;
import com.server.manager.rule.RuleLoadManager;
import com.server.tools.FactLoader;
import com.server.utility.TestNgBase;
import com.server.utility.template.*;
import com.server.utility.template.condition.BaseConditionTemplate;
import com.server.utility.template.condition.EqualConditionTemplate;
import com.server.utility.template.condition.RangeConditionTemplate;
import org.drools.core.io.impl.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/24/18
 * Time: 9:54 AM
 */
public class ProcessTest extends TestNgBase {

    @Autowired
    private RuleLoadManager ruleLoadManager;

    @Autowired
    private FactLoader factLoader;

    @Autowired
    private KnowLedgeBaseManger knowLedgeBaseManger;

    @Autowired
    private QueryManager queryManager;

    public static final String PACKAGENAME = "com.server.project.wms4";

    /*public static final String PACKAGENAME = "com.server.command";*/

    public List<FactClassDescriptionInfo> loadTestFact() throws IOException {
        factLoader.setClassPath("/command/*.class");
        return factLoader.entityInfo();
    }

    @Test
    @Transactional
    public void saveRule() {
        /*RuleCommand ruleCommand = new RuleCommand();
        ruleCommand.setRuleName("测试");
        ruleCommand.setPackageName(PACKAGENAME);
        ruleCommand.setCreate_Time(new Date());
        ruleCommand.setRemarks("测试用规则1");
        ruleCommand.setRuleString(null);
        ruleCommand.setState(1);

        List<RuleGroup> list = new ArrayList<>();
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setGroupCode("测试一组");
        ruleGroup.setOrderNo("1");
        ruleGroup.setCreate_time(new Date());
        list.add(ruleGroup);
        ruleCommand.setRuleGroup(list);

        List<RuleGlobal> ruleGlobals = new ArrayList<>();
        RuleGlobal ruleGlobal = new RuleGlobal();
        ruleGlobal.setCreate_time(new Date());
        ruleGlobal.setGlobalName("list");
        ruleGlobal.setGlobalType(List.class.getName());
        ruleGlobals.add(ruleGlobal);

        ruleCommand.setRuleGlobals(ruleGlobals);

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
        ruleCommand.setRuleInfos(ruleInfoList);

        List<RuleOp> ruleOps = new ArrayList<>();
        RuleOp ruleOp = new RuleOp();
        ruleOp.setAttribute("message");
        ruleOp.setCreate_time(new Date());
        ruleOp.setObjName("OdoCommand");
        ruleOp.setType(BaseOperationTemplate.SIMPLEOPERATIONTYPE);
        ruleOp.setValue("\"111111\"");
        ruleOps.add(ruleOp);

        ruleCommand.setRuleOps(ruleOps);
        ruleLoadManager.saveRule(ruleCommand);*/
    }

    @Test
    public void deleteRule() {
        /*List<RuleHead> ruleHeads = ruleLoadManager.findAllRuleHead();
        ruleHeads.forEach(ruleHead -> {
            ruleLoadManager.deleteRule(ruleHead.getId());
        });*/
    }

    @Test
    public void loadAllRuleForDB() {
        Map<String, List<BaseResource>> map = ruleLoadManager.loadAllRules();
    }

    @Test
    public void loadRuleForDB() {
        List<BaseResource> rule = ruleLoadManager.loadRule("测试一组");
        System.out.println(rule.size());
    }

    @Test
    public void checkRule() {
        Map<String, List<BaseResource>> map = ruleLoadManager.loadAllRules();

        map.forEach((group, resourceList) -> {
            KnowledgeMessage error = knowLedgeBaseManger.testRule(resourceList);
            System.out.println("--------------------" + error.getMessage());
        });
        List<BaseResource> rule = ruleLoadManager.loadRule("测试一组");
        KnowledgeMessage errort = knowLedgeBaseManger.testRule(rule);
        System.out.println("--------------------" + errort.getMessage());
    }

    @Test
    public void testProce(){
        deleteRule();
        loadAllRuleForDB();
        saveRule();
        loadRuleToKnowLedgeBaseManger();
    }

    @Test
    public void loadRuleToKnowLedgeBaseManger() {
        Map<String, List<BaseResource>> map = ruleLoadManager.loadAllRules();
        map.forEach((group, resourceList) -> {
            knowLedgeBaseManger.createKnowledgeBase(resourceList, group);
        });


        System.out.println("======================================================");
        List<OdoCommand> odoCommands = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            odoCommands.add(dataBuilders(i));
        }

        System.out.println("======================================================");

        long start = System.currentTimeMillis();
        //queryManager.queryCommandWithKieSessionAsList("测试一组", odoCommands);
        queryManager.queryCommandWithStatelessKieSessionAsList("测试一组", odoCommands);
        System.out.println("=============================" + (System.currentTimeMillis() - start));
        /*int count=0;
        for (OdoCommand odoCommand : odoCommands) {
            System.out.println(odoCommand.getCustomerId() + "    " + odoCommand.getOdoIndex() + "          " + odoCommand.getOdoType() + "    " + odoCommand.getMessage());
            for (WhOdoLineCommand whOdoLineCommand : odoCommand.getWhOdoLineCommands()) {
                System.out.println("             --------------------" + whOdoLineCommand.getSkuBarCode());
                System.out.println("             --------------------" + whOdoLineCommand.getQty());
            }
            if(null!=odoCommand.getMessage()){
                count++;
            }
        }
        System.out.println(count);*/
        int count=0;
        for (int i = 0; i < odoCommands.size(); i++) {
            if (null != odoCommands.get(i).getMessage())
                count++;
        }
        System.out.println(count);

        System.out.println("======================================================");

    }

    public static OdoCommand dataBuilders(int i) {
        String t = "" + i;
        OdoCommand odoCommand = new OdoCommand();
        odoCommand.setAmt(new Double(1));
        odoCommand.setOuId(Long.valueOf(t));
        odoCommand.setOdoType(String.valueOf(new Random().nextInt(5)));
        odoCommand.setCustomerId(Long.valueOf(new Random().nextInt(2)));
        odoCommand.setOdoIndex(String.valueOf(new Random().nextInt(2)));
        odoCommand.setActualQty(Double.parseDouble(String.valueOf(new Random().nextInt(10))));
        List<WhOdoLineCommand> whOdoLineCommands = new ArrayList<WhOdoLineCommand>();
        WhOdoLineCommand whOdoLineCommand1 = new WhOdoLineCommand();
        whOdoLineCommand1.setQty(Double.parseDouble(t));
        whOdoLineCommand1.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand1.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand1);
        WhOdoLineCommand whOdoLineCommand2 = new WhOdoLineCommand();
        whOdoLineCommand2.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand2.setQty(Double.parseDouble(t));
        whOdoLineCommand2.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand2);
        WhOdoLineCommand whOdoLineCommand3 = new WhOdoLineCommand();
        whOdoLineCommand3.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand3.setQty(Double.parseDouble(t));
        whOdoLineCommand3.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand3);
        WhOdoLineCommand whOdoLineCommand4 = new WhOdoLineCommand();
        whOdoLineCommand4.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand4.setQty(Double.parseDouble(t));
        whOdoLineCommand4.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand4);
        WhOdoLineCommand whOdoLineCommand5 = new WhOdoLineCommand();
        whOdoLineCommand5.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand5.setQty(Double.parseDouble(t));
        whOdoLineCommand5.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand5);

        odoCommand.setWhOdoLineCommands(whOdoLineCommands);
        return odoCommand;
    }


    public static OdoCommand dataBuilder(int i) {
        String t = "" + i;
        OdoCommand odoCommand = new OdoCommand();
        odoCommand.setAmt(new Double(1));
        odoCommand.setOuId(Long.valueOf(t));
        odoCommand.setOdoType(String.valueOf(new Random().nextInt(5)));
        odoCommand.setCustomerId(Long.valueOf(new Random().nextInt(2)));
        odoCommand.setOdoIndex(String.valueOf(new Random().nextInt(2)));
        odoCommand.setActualQty(Double.parseDouble(String.valueOf(new Random().nextInt(10))));
        List<WhOdoLineCommand> whOdoLineCommands = new ArrayList<>();
        WhOdoLineCommand whOdoLineCommand1 = new WhOdoLineCommand();
        whOdoLineCommand1.setQty(Double.parseDouble(t));
        whOdoLineCommand1.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand1.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand1);
        //if (i % 2 > 0) {
            WhOdoLineCommand whOdoLineCommand2 = new WhOdoLineCommand();
            whOdoLineCommand2.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
            whOdoLineCommand2.setQty(Double.parseDouble(t));
            whOdoLineCommand2.setActualQty(new Random(i).nextDouble());
            whOdoLineCommands.add(whOdoLineCommand2);
        //}
        odoCommand.setWhOdoLineCommands(whOdoLineCommands);
        return odoCommand;
    }

}
