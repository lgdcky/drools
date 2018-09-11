package com.server.manager.init;

import com.server.knowledgebasemanager.KnowLedgeBaseManger;
import com.server.manager.rule.RuleLoadManager;
import org.drools.core.io.impl.BaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/11/18
 * Time: 9:44 AM
 */
@Component
public class RuleInitManagerImpl implements RuleInitManager {

    private static Logger logger = LoggerFactory.getLogger(RuleInitManagerImpl.class);

    @Autowired
    private RuleLoadManager ruleLoadManager;

    @Autowired
    private KnowLedgeBaseManger knowLedgeBaseManger;

    @Override
    @PostConstruct
    public void initRule() {
        logger.info("Initialization rule");
        Map<String, List<BaseResource>> map = ruleLoadManager.loadAllRules();
        map.forEach((group, resourceList) -> {
            knowLedgeBaseManger.createKnowledgeBase(resourceList, group);
        });
        logger.info("rule Initialization completed!");
    }
}
