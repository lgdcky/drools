package com.server.controller;

import com.server.manager.rule.RuleOperationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 6:42 PM
 */
@Controller
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    private RuleOperationManager ruleOperationManager;

    /**
     * 检查规则是否正确
     * @return
     */
    @GetMapping(path = "/checkRule")
    @ResponseBody
    public String checkRule() {
        //TODO
        return "";
    }

    /**
     * 保存规则
     * @return
     */
    @GetMapping(path = "/saveRule")
    @ResponseBody
    public String saveRule() {
        //TODO
        return "";
    }
}
