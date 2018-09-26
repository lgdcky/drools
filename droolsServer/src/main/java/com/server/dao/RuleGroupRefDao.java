package com.server.dao;

import com.server.model.RuleGroupRef;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/19/18
 * Time: 11:39 AM
 */
@Mapper
public interface RuleGroupRefDao {

    Long saveRuleGroupRef(RuleGroupRef ruleGroupRef);

    Long deleteRuleGroupRefByParameter(RuleGroupRef ruleGroupRef);

    Long updateRuleGroupRefByParameter(RuleGroupRef ruleGroupRef);

}
