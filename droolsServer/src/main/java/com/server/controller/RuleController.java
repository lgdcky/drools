package com.server.controller;

import com.server.MessageCommand.RuleMessage;
import com.server.command.RuleCommand;
import com.server.manager.rule.RuleOperationManager;
import com.server.model.RuleGroup;
import com.server.model.RuleGroupRef;
import com.server.model.RuleHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.server.tools.TypeConvertTools.objectListToJson;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 6:42 PM
 */
@Controller
@RequestMapping("/rule")
public class RuleController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(RuleController.class);

    @Autowired
    private RuleOperationManager ruleOperationManager;

    /**
     * 检查规则是否正确
     *
     * @return
     */
    @GetMapping(path = "/checkRule")
    @ResponseBody
    public String checkRule(@RequestParam("ruleCommand") RuleCommand ruleCommand) {
        List<RuleCommand> ruleCommandList = new ArrayList<>();
        ruleCommandList.add(ruleCommand);
        try {
            return objectListToJson(ruleOperationManager.checkRule(ruleCommandList));
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.toString());
            try {
                return objectListToJson(new RuleMessage("return result error", e.getMessage(), false, null));
            } catch (IOException | ClassNotFoundException ex) {
                log.error(e.toString());
            }
        }
        return null;
    }

    /**
     * 保存规则
     *
     * @return
     */
    @GetMapping(path = "/saveRule")
    @ResponseBody
    public String saveRule(@RequestParam("ruleHead") RuleHead ruleHead) {
        try {
            return objectListToJson(ruleOperationManager.createRule(ruleHead));
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.toString());
            try {
                return objectListToJson(new RuleMessage("return save rule error", e.getMessage(), false, null));
            } catch (IOException | ClassNotFoundException ex) {
                log.error(e.toString());
            }
        }
        return null;
    }

    /**
     * 保存组信息
     *
     * @return
     */
    @GetMapping(path = "/saveRuleGroup")
    @ResponseBody
    public String saveRuleGroup(@RequestParam("ruleGroup") RuleGroup ruleGroup) {
        try {
            return objectListToJson(ruleOperationManager.createRuleGroup(ruleGroup));
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.toString());
            try {
                return objectListToJson(new RuleMessage("return save rule group error", e.getMessage(), false, null));
            } catch (IOException | ClassNotFoundException ex) {
                log.error(e.toString());
            }
        }
        return null;
    }

    /**
     * 更新组信息
     *
     * @return
     */
    @GetMapping(path = "/updateRuleGroup")
    @ResponseBody
    public String updateRuleGroup(@RequestParam("ruleGroup") RuleGroup ruleGroup) {
        try {
            return objectListToJson(ruleOperationManager.updateRuleGroup(ruleGroup));
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.toString());
            try {
                return objectListToJson(new RuleMessage("return update rule group error", e.getMessage(), false, null));
            } catch (IOException | ClassNotFoundException ex) {
                log.error(e.toString());
            }
        }
        return null;
    }

    /**
     * 返回组信息
     *
     * @return
     */
    @GetMapping(path = "/findRuleGroup")
    @ResponseBody
    public String findRuleGroup(@RequestParam("ruleGroup") RuleGroup ruleGroup, @RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size) {
        try {
            return objectListToJson(ruleOperationManager.findRuleGroupWithPage(ruleGroup, pageNum, size));
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.toString());
            try {
                return objectListToJson(new RuleMessage("return find rule group error", e.getMessage(), false, null));
            } catch (IOException | ClassNotFoundException ex) {
                log.error(e.toString());
            }
        }
        return null;
    }

    /**
     * 返回规则信息
     *
     * @return
     */
    @GetMapping(path = "/findRule")
    @ResponseBody
    public String findRule(@RequestParam("ruleGroup") RuleGroup ruleGroup, @RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size) {
        try {
            return objectListToJson(ruleOperationManager.findRuleByRuleGroupWithPage(ruleGroup, pageNum, size));
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.toString());
            try {
                return objectListToJson(new RuleMessage("return find rule error", e.getMessage(), false, null));
            } catch (IOException | ClassNotFoundException ex) {
                log.error(e.toString());
            }
        }
        return null;
    }

    /**
     * 返回规则信息
     *
     * @return
     */
    @GetMapping(path = "/updateRule")
    @ResponseBody
    public String updateRule(@RequestParam("ruleHead") RuleHead ruleHead) {
        try {
            return objectListToJson(ruleOperationManager.updateRule(ruleHead));
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.toString());
            try {
                return objectListToJson(new RuleMessage("return update rule error", e.getMessage(), false, null));
            } catch (IOException | ClassNotFoundException ex) {
                log.error(e.toString());
            }
        }
        return null;
    }

    /**
     * 更新组下规则状态
     *
     * @return
     */
    @GetMapping(path = "/updateRuleGroupRef")
    @ResponseBody
    public String updateRuleGroupRef(@RequestParam("ruleGroupRef") RuleGroupRef ruleGroupRef) {
        try {
            return objectListToJson(ruleOperationManager.updateRuleGroupRef(ruleGroupRef));
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.toString());
            try {
                return objectListToJson(new RuleMessage("return updateRuleGroupRef error", e.getMessage(), false, null));
            } catch (IOException | ClassNotFoundException ex) {
                log.error(e.toString());
            }
        }
        return null;
    }

    /**
     * 删除组与规则关系
     *
     * @return
     */
    @GetMapping(path = "/deleteRuleGroupRef")
    @ResponseBody
    public String deleteRuleGroupRef(@RequestParam("ruleGroupRef") RuleGroupRef ruleGroupRef) {
        try {
            return objectListToJson(ruleOperationManager.deleteRuleGroupRef(ruleGroupRef));
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.toString());
            try {
                return objectListToJson(new RuleMessage("return deleteRuleGroupRef error", e.getMessage(), false, null));
            } catch (IOException | ClassNotFoundException ex) {
                log.error(e.toString());
            }
        }
        return null;
    }

    /**
     * 根据ID删除规则
     *
     * @return
     */
    @GetMapping(path = "/deleteRuleById")
    @ResponseBody
    public String deleteRuleById(@RequestParam("ids") List<Long> ruleIds) {
        try {
            return objectListToJson(ruleOperationManager.deleteRuleById(ruleIds));
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.toString());
            try {
                return objectListToJson(new RuleMessage("return deleteRuleById error", e.getMessage(), false, null));
            } catch (IOException | ClassNotFoundException ex) {
                log.error(e.toString());
            }
        }
        return null;
    }

    /**
     * 删除组
     *
     * @return
     */
    @GetMapping(path = "/deleteRuleGroup")
    @ResponseBody
    public String deleteRuleGroup(@RequestParam("ruleGroup") RuleGroup ruleGroup) {
        try {
            return objectListToJson(ruleOperationManager.deleteRuleGroup(ruleGroup));
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.toString());
            try {
                return objectListToJson(new RuleMessage("return deleteRuleGroup error", e.getMessage(), false, null));
            } catch (IOException | ClassNotFoundException ex) {
                log.error(e.toString());
            }
        }
        return null;
    }
}
