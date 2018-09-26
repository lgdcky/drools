package com.server.dao;

import com.server.model.RuleGroup;
import com.server.model.RuleGroupRef;
import com.server.utility.TestNgBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/19/18
 * Time: 1:52 PM
 */
public class RuleDaoTest extends TestNgBase {

    @Autowired
    private RuleDao ruleDao;

    @Test
    public void testFindRuleById() {
        assertEquals(ruleDao.findRuleById(1l),null);
    }

    @Test
    public void testFindAllRule() {
        assertEquals(ruleDao.findAllRule().size(),0);
    }

    @Test
    public void testFindAllRuleByGroup() {
        assertEquals(ruleDao.findAllRuleByGroup("测试").size(),0);
    }

    @Test
    public void testFindAllRuleByGroupWithPage() {
        Map<String,Object> map = new HashMap<>();
        map.put("groupCode","测试");
        assertEquals(ruleDao.findAllRuleByGroupWithPage(map).size(),0);
    }
}