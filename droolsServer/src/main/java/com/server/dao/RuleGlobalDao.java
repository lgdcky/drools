package com.server.dao;


import com.server.model.RuleGlobal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/21/18
 * Time: 10:36 AM
 */
@Mapper
public interface RuleGlobalDao {

    void saveRuleGlobal(RuleGlobal ruleGlobal);

    List<RuleGlobal> findRuleGlobalByParameter(RuleGlobal ruleGlobal);

    Long deleteRuleGlobalByParameter(RuleGlobal ruleGlobal);

    Long updateRuleGlobalByParameter(RuleGlobal ruleGlobal);

}
