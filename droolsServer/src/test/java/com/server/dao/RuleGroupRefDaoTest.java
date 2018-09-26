package com.server.dao;

import com.server.model.RuleGroupRef;
import com.server.utility.TestNgBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/19/18
 * Time: 2:03 PM
 */
public class RuleGroupRefDaoTest extends TestNgBase {

    @Autowired
    private RuleGroupRefDao ruleGroupRefDao;

    @Test
    public void testSaveRuleGroupRef() {
        RuleGroupRef ruleGroupRef = new RuleGroupRef();
        ruleGroupRef.setCreate_time(new Date());
        ruleGroupRef.setOrderNo(1);
        ruleGroupRef.setRule_id(1l);
        ruleGroupRef.setRuleGroup_id(1l);
        long count = ruleGroupRefDao.saveRuleGroupRef(ruleGroupRef);
        assertEquals(count,1l);
    }

    @Test
    public void testDeleteRuleGroupRefByParameter() {
        RuleGroupRef ruleGroupRef = new RuleGroupRef();
        ruleGroupRef.setCreate_time(new Date());
        ruleGroupRef.setOrderNo(1);
        long count = ruleGroupRefDao.deleteRuleGroupRefByParameter(ruleGroupRef);
        assertEquals(count,1l);
    }
}