package com.server.dao;

import com.server.command.RuleCommand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/22/18
 * Time: 6:31 PM
 */
@Mapper
public interface RuleDao {

    RuleCommand findRuleById(Long id);

    List<RuleCommand> findAllRule();

    List<RuleCommand> findAllRuleByGroup(String knowledgeBaseName);

    List<RuleCommand> findAllRuleByGroupWithPage(Map<String,Object> map);

}
