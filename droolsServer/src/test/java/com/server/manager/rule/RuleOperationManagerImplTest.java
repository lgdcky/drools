package com.server.manager.rule;

import com.server.MessageCommand.RuleMessage;
import com.server.model.RuleGroup;
import com.server.model.RuleHead;
import com.server.utility.TestNgBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/18/18
 * Time: 4:58 PM
 */
public class RuleOperationManagerImplTest extends TestNgBase {

    @Autowired
    private RuleOperationManager ruleOperationManager;

    @Test
    public void testFindRuleHeadWithPage() {
        RuleHead ruleHead = new RuleHead();
        ruleHead.setRuleName("测试");
        RuleMessage ruleMessage = ruleOperationManager.findRuleHeadWithPage(ruleHead,1,0);
        List<RuleHead> ruleHeadList = ruleMessage.getData();
        Assert.assertEquals(ruleHeadList.size(),0);
    }

    @Test
    public void testFindRuleByRuleGroupWithPage() {
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setGroupCode("测试一组");
        RuleMessage ruleMessage = ruleOperationManager.findRuleByRuleGroupWithPage(ruleGroup,0,1);
        List<RuleGroup> ruleGroupList = ruleMessage.getData();
        Assert.assertEquals(ruleGroupList.size(),1);

    }
}