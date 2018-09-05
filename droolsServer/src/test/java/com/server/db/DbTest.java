package com.server.db;

import com.server.command.RuleCommand;
import com.server.dao.*;
import com.server.model.*;
import com.server.ruleManager.RuleLoadManager;
import com.server.utility.TestNgBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/21/18
 * Time: 3:17 PM
 */
public class DbTest extends TestNgBase {

    @Autowired
    private RuleHeadDao ruleHeadDao;

    @Autowired
    private RuleGroupDao ruleGroupDao;

    @Autowired
    private RuleLoadManager ruleLoadManager;

    @Autowired
    private RuleGlobalDao ruleGlobalDao;

    @Autowired
    private RuleInfoDao ruleInfoDao;

    @Autowired
    private RuleConditionDao ruleConditionDao;

    @Autowired
    private RuleDao ruleDao;

    @Test
    @Transactional
    public void testSaveRuleHead(){
        RuleHead ruleHead = new RuleHead();
        ruleHead.setRuleName("测试");
        ruleHead.setPackageName("com.server.command");
        ruleHead.setCreate_Time(new Date());
        ruleHeadDao.saveRuleHead(ruleHead);
        System.out.println(ruleHead.getId());
    }

    @Test
    @Transactional
    public void testFindRuleHead(){
        RuleHead ruleHead = new RuleHead();
        ruleHead.setId(11l);
        System.out.println(ruleHeadDao.findRuleHead(ruleHead).toString());
        System.out.println();
    }

    @Test
    @Transactional
    public void testDeleteRuleHead(){
        RuleHead ruleHead = new RuleHead();
        ruleHead.setId(11l);
        System.out.println(ruleHeadDao.deleteRuleHeadByParameter(ruleHead));
    }

    @Test
    @Transactional
    public void testUpdateRuleHead(){
        RuleHead ruleHead = new RuleHead();
        ruleHead.setId(12l);
        ruleHead.setRuleName("测试12");
        ruleHead.setPackageName("com.server.command");
        ruleHead.setCreate_Time(new Date());
        ruleHeadDao.updateRuleHeadByParameter(ruleHead);
    }





    @Test
    @Transactional
    public void testSaveRuleGroup(){
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setGroupCode("cc");
        ruleGroup.setOrderNo("1");
        ruleGroup.setRule_id(12l);
        ruleGroup.setCreate_time(new Date());
        ruleGroupDao.saveRuleGroup(ruleGroup);
    }

    @Test
    @Transactional
    public void testFindRuleGroup(){
        /*RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setRule_id(12l);
        ruleGroup = ruleGroupDao.findRuleGroupByParameter(ruleGroup);
        System.out.println();*/
    }

    @Test
    @Transactional
    public void testDeleteRuleGroup(){
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setRule_id(14l);
        System.out.println(ruleGroupDao.deleteRuleGroupByParameter(ruleGroup));
    }

    @Test
    @Transactional
    public void testUpdateRuleGroup(){
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setGroupCode("cc12");
        ruleGroup.setOrderNo("2");
        ruleGroup.setRule_id(12l);
        ruleGroup.setCreate_time(new Date());
        ruleGroupDao.updateRuleGroupByParameter(ruleGroup);
    }


    @Test
    @Transactional
    public void saveRule(){
        RuleCommand ruleCommand = new RuleCommand();
        ruleCommand.setRuleName("测试");
        ruleCommand.setPackageName("com.server.command");
        ruleCommand.setCreate_Time(new Date());
        ruleCommand.setRemarks("test");
        ruleCommand.setRuleString("ddddd");
        ruleCommand.setState(1);

        List<RuleGroup> list = new ArrayList<>();
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setGroupCode("cc12");
        ruleGroup.setOrderNo("2");
        ruleGroup.setCreate_time(new Date());
        list.add(ruleGroup);
        ruleCommand.setRuleGroup(list);

        List<RuleGlobal> ruleGlobals = new ArrayList<>();
        RuleGlobal ruleGlobal = new RuleGlobal();
        ruleGlobal.setCreate_time(new Date());
        ruleGlobal.setGlobalName("aa");
        ruleGlobal.setGlobalType("cc");
        ruleGlobals.add(ruleGlobal);

        ruleCommand.setRuleGlobals(ruleGlobals);

        List<RuleInfo> ruleInfoList = new ArrayList<>();
        RuleInfo ruleInfo = new RuleInfo();
        ruleInfo.setAttribute("cc");
        ruleInfo.setCreate_time(new Date());
        ruleInfo.setCalculation("tt");
        ruleInfo.setObj("ob");
        ruleInfo.setParent("pp");
        ruleInfo.setType("ttc");



        List<RuleCondition> ruleConditions = new ArrayList<>();
        RuleCondition ruleCondition = new RuleCondition();
        ruleCondition.setAssociationType("aaa");
        ruleCondition.setConditionKey("cc");
        ruleCondition.setConditionValue("ccv");
        ruleCondition.setOp("op");
        ruleCondition.setCreate_time(new Date());
        ruleConditions.add(ruleCondition);
        ruleInfo.setRuleConditions(ruleConditions);

        ruleInfoList.add(ruleInfo);
        ruleCommand.setRuleInfos(ruleInfoList);

        List<RuleOp> ruleOps = new ArrayList<>();
        RuleOp ruleOp = new RuleOp();
        ruleOp.setAttribute("aa");
        ruleOp.setCreate_time(new Date());
        ruleOp.setObjName("tt");
        ruleOp.setType("jj");
        ruleOp.setValue("dddd");
        ruleOps.add(ruleOp);

        ruleCommand.setRuleOps(ruleOps);
        ruleLoadManager.saveRule(ruleCommand);

    }

    @Test
    @Transactional
    public void findAllRule(){
        ruleLoadManager.loadAllRules();
    }


    @Test
    @Transactional
    public void deleteRule(){
        ruleLoadManager.deleteRule(14l);
    }


    @Test
    @Transactional
    public void updateRule(){
        RuleCommand ruleCommand = new RuleCommand();
        ruleCommand.setId(13l);
        ruleCommand.setRuleName("测试12344");
        ruleCommand.setPackageName("com.server.command");
        ruleCommand.setCreate_Time(new Date());
        ruleCommand.setRemarks("test");
        ruleCommand.setRuleString("ddddd");
        ruleCommand.setState(1);

        List<RuleGroup> list = new ArrayList<>();
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setRule_id(13l);
        ruleGroup.setGroupCode("cc1244");
        ruleGroup.setOrderNo("2");
        ruleGroup.setCreate_time(new Date());
        list.add(ruleGroup);
        ruleCommand.setRuleGroup(list);

        List<RuleGlobal> ruleGlobals = new ArrayList<>();
        RuleGlobal ruleGlobal = new RuleGlobal();
        ruleGlobal.setRule_id(13l);
        ruleGlobal.setCreate_time(new Date());
        ruleGlobal.setGlobalName("aa44");
        ruleGlobal.setGlobalType("cc");
        ruleGlobals.add(ruleGlobal);

        ruleCommand.setRuleGlobals(ruleGlobals);

        List<RuleInfo> ruleInfoList = new ArrayList<>();
        RuleInfo ruleInfo = new RuleInfo();
        ruleInfo.setRule_id(13l);
        ruleInfo.setAttribute("cc444");
        ruleInfo.setCreate_time(new Date());
        ruleInfo.setCalculation("tt");
        ruleInfo.setObj("ob");
        ruleInfo.setParent("pp");
        ruleInfo.setType("ttc");



        List<RuleCondition> ruleConditions = new ArrayList<>();
        RuleCondition ruleCondition = new RuleCondition();
        ruleCondition.setRuleInfo_id(9l);
        ruleCondition.setAssociationType("aaa444");
        ruleCondition.setConditionKey("cc");
        ruleCondition.setConditionValue("ccv");
        ruleCondition.setOp("op");
        ruleCondition.setCreate_time(new Date());
        ruleConditions.add(ruleCondition);
        ruleInfo.setRuleConditions(ruleConditions);

        ruleInfoList.add(ruleInfo);
        ruleCommand.setRuleInfos(ruleInfoList);

        List<RuleOp> ruleOps = new ArrayList<>();
        RuleOp ruleOp = new RuleOp();
        ruleOp.setRule_id(13l);
        ruleOp.setAttribute("aa444");
        ruleOp.setCreate_time(new Date());
        ruleOp.setObjName("tt");
        ruleOp.setType("jj");
        ruleOp.setValue("dddd");
        ruleOps.add(ruleOp);

        ruleCommand.setRuleOps(ruleOps);
        ruleLoadManager.updateRule(ruleCommand);
    }

    @Test
    public void getRule(){
        ruleLoadManager.loadRule("cc12");
    }

}
