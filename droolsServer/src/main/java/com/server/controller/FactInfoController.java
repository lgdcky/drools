package com.server.controller;

import com.server.manager.fact.FactManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 2:15 PM
 */
@Controller
@RequestMapping("/fact")
public class FactInfoController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(FactInfoController.class);

    @Autowired
    private FactManager factManager;

    private static final String PATH="/project/wms4/*.class";

    /**
     * 返回项目使用的fact对象类信息
     * @return
     */
    @GetMapping(path = "/factList")
    @ResponseBody
    public String factList() {
        return factManager.getFactClassDescriptionInfo(PATH);
    }

}
