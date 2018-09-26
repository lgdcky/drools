package com.server.dao;

import com.server.model.RuleGroup;
import com.server.utility.TestNgBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/19/18
 * Time: 2:11 PM
 */
public class RuleGroupDaoTest extends TestNgBase {

    @Autowired
    private RuleGroupDao ruleGroupDao;

    @Test
    public void testSaveRuleGroup() {
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setGroupCode("测试");
        ruleGroup.setCreate_time(new Date());
        ruleGroup.setDescription("测试描述");
        ruleGroup.setGroupName("测试名称");
        long count = ruleGroupDao.saveRuleGroup(ruleGroup);
        assertEquals(count,1l);
    }

    @Test
    public void testFindRuleGroupByParameter() {
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setGroupCode("测试");
        assertEquals(ruleGroupDao.findRuleGroupByParameter(ruleGroup).size(),1l);
    }

    @Test
    public void testFindRuleGroupByParameterWithPage() {
        Map<String,Object> map = new HashMap<>();
        map.put("groupCode","测试");
        assertEquals(ruleGroupDao.findRuleGroupByParameterWithPage(map).size(),1l);
    }

    @Test
    public void testDeleteRuleGroupByParameter() {
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setGroupCode("测试");
        long count = ruleGroupDao.deleteRuleGroupByParameter(ruleGroup);
        assertEquals(count,1l);
    }

    @Test
    public void testUpdateRuleGroupByParameter() {
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setId(4l);
        ruleGroup.setGroupCode("测试");
        ruleGroup.setCreate_time(new Date());
        ruleGroup.setDescription("测试描述");
        ruleGroup.setGroupName("测试名称1");
        long count = ruleGroupDao.updateRuleGroupByParameter(ruleGroup);
        assertEquals(count,1l);
    }
}