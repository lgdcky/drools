package com.server.dao;


import com.server.command.RuleCommand;
import com.server.model.RuleInfo;
import org.apache.ibatis.annotations.MapKey;
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
public interface RuleInfoDao {

    Long saveRuleInfo(RuleInfo ruleInfo);

    List<RuleInfo> findRuleInfoByParameter(RuleInfo ruleInfo);

    Long deleteRuleInfoByParameter(RuleInfo ruleInfo);

    Long updateRuleInfoByParameter(RuleInfo ruleInfo);

}
