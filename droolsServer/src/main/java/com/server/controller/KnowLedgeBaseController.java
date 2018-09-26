package com.server.controller;

import com.server.MessageCommand.KnowledgeMessage;
import com.server.command.RuleCommand;
import com.server.manager.knowledge.KnowLedgeBaseManger;
import com.server.manager.rule.RuleLoadManager;
import com.server.model.RuleGroup;
import org.drools.core.io.impl.BaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.server.MessageCommand.KnowledgeMessage.FAILED;
import static com.server.MessageCommand.KnowledgeMessage.SUCCESS;
import static com.server.tools.TypeConvertTools.objectListToJson;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/19/18
 * Time: 6:12 PM
 */
@Controller
@RequestMapping("/knowLedgeBase")
public class KnowLedgeBaseController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(KnowLedgeBaseController.class);

    @Autowired
    private KnowLedgeBaseManger knowLedgeBaseManger;

    @Autowired
    private RuleLoadManager ruleLoadManager;

    /**
     * 重载所有规则
     *
     * @return
     */
    @GetMapping(path = "/reLoadAllRule")
    @ResponseBody
    public String reLoadAllRule() {
        try {
            knowLedgeBaseManger.reloadRules();
            return objectListToJson(new KnowledgeMessage("reLoadAllRule success", SUCCESS, null));
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.toString());
            try {
                return objectListToJson(new KnowledgeMessage("return result error", FAILED, e.getMessage()));
            } catch (IOException | ClassNotFoundException ex) {
                log.error(e.toString());
            }
        }
        return null;
    }

    /**
     * 重载组规则
     *
     * @return
     */
    @GetMapping(path = "/reLoadRuleByRuleGroup")
    @ResponseBody
    public String reLoadRuleByRuleGroup(@RequestParam("ruleGroup") RuleGroup ruleGroup) {
        try {
            knowLedgeBaseManger.reloadRule(ruleGroup.getGroupCode());
            return objectListToJson(new KnowledgeMessage("reLoadRuleByRuleGroup success", SUCCESS, null));
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.toString());
            try {
                return objectListToJson(new KnowledgeMessage("return result error", FAILED, e.getMessage()));
            } catch (IOException | ClassNotFoundException ex) {
                log.error(e.toString());
            }
        }
        return null;
    }

}
