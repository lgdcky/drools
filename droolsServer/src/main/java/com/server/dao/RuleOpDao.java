package com.server.dao;


import com.server.model.RuleOp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/21/18
 * Time: 10:36 AM
 */
@Mapper
public interface RuleOpDao {

    void saveRuleOp(RuleOp ruleOp);

    List<RuleOp> findRuleOpByParameter(RuleOp ruleOp);

    Long deleteRuleOpByParameter(RuleOp ruleOp);

    Long updateRuleOpByParameter(RuleOp ruleOp);

}
