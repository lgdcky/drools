package com.server.dao;


import com.server.model.RuleHead;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/21/18
 * Time: 10:36 AM
 */
@Mapper
public interface RuleHeadDao {

    List<RuleHead> findAllRuleHeads();

    Long saveRuleHead(RuleHead ruleHead);

    List<RuleHead> findRuleHead(RuleHead ruleHead);

    List<RuleHead> findRuleHeadWithPage(Map<String,Object> map);

    Long deleteRuleHeadByParameter(RuleHead ruleHead);

    Long updateRuleHeadByParameter(RuleHead ruleHead);

}
