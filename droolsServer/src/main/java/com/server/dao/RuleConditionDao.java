package com.server.dao;


import com.server.model.RuleCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/21/18
 * Time: 10:36 AM
 */
@Mapper
public interface RuleConditionDao {

    void saveRuleCondition(RuleCondition ruleCondition);

    List<RuleCondition> findRuleConditionByParameter(RuleCondition ruleCondition);

    Long deleteRuleConditionByParameter(RuleCondition ruleCondition);

    Long updateRuleConditionByParameter(RuleCondition ruleCondition);

}
