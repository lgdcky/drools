package com.server.dao;


import com.server.model.RuleGroup;
import com.server.model.RuleHead;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/21/18
 * Time: 10:36 AM
 */
@Mapper
public interface RuleGroupDao {

    void saveRuleGroup(RuleGroup ruleGroup);

    List<RuleGroup> findRuleGroupByParameter(RuleGroup ruleGroup);

    Long deleteRuleGroupByParameter(RuleGroup ruleGroup);

    Long updateRuleGroupByParameter(RuleGroup ruleGroup);

}
