package com.server.dao;

import com.server.model.RuleHead;
import com.server.utility.TestNgBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/19/18
 * Time: 2:48 PM
 */
public class RuleHeadDaoTest extends TestNgBase {

    @Autowired
    private RuleHeadDao ruleHeadDao;

    @Test
    public void testFindAllRuleHeads() {
        assertEquals(ruleHeadDao.findAllRuleHeads().size(),1l);
    }

    @Test
    public void testSaveRuleHead() {
        RuleHead ruleHead = new RuleHead();
        ruleHead.setRuleName("测试");
        ruleHead.setPackageName("com");
        ruleHead.setCreate_Time(new Date());
        ruleHead.setRemarks("测试");
        ruleHead.setRuleString("tttt");
        long count = ruleHeadDao.saveRuleHead(ruleHead);
        assertEquals(count,1l);
    }

    @Test
    public void testFindRuleHead() {
        RuleHead ruleHead = new RuleHead();
        ruleHead.setRuleName("测试");
        assertEquals(ruleHeadDao.findRuleHead(ruleHead).size(),1l);
    }

    @Test
    public void testFindRuleHeadWithPage() {
        Map<String,Object> map = new HashMap<>();
        map.put("ruleName","测试");
        map.put("pageNum",1);
        map.put("size",1);
        assertEquals(ruleHeadDao.findRuleHeadWithPage(map).size(),0l);
    }

    @Test
    public void testUpdateRuleHeadByParameter() {
        RuleHead ruleHead = new RuleHead();
        ruleHead.setId(1l);
        ruleHead.setRuleName("测试");
        ruleHead.setPackageName("com");
        ruleHead.setCreate_Time(new Date());
        ruleHead.setRemarks("测试1");
        ruleHead.setRuleString("tttt");
        long count = ruleHeadDao.updateRuleHeadByParameter(ruleHead);
        assertEquals(count,1l);
    }

    @Test
    public void testDeleteRuleHeadByParameter() {
        RuleHead ruleHead = new RuleHead();
        ruleHead.setId(1l);
        long count = ruleHeadDao.deleteRuleHeadByParameter(ruleHead);
        assertEquals(count,1l);
    }
}