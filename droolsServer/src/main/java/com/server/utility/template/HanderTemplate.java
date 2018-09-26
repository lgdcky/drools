package com.server.utility.template;

import org.drools.compiler.lang.api.*;
import org.drools.compiler.lang.descr.AndDescr;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/15/18
 * Time: 1:50 PM
 */
public class HanderTemplate {


    private PackageDescrBuilder packageDescrBuilder = DescrFactory.newPackage();

    private CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder;


    /**
     * 创建全局变量
     *
     * @param global
     * @return
     */
    public PackageDescrBuilder addGlobal(Map<String, String> global) {
        GlobalDescrBuilder globalDescrBuilder = packageDescrBuilder.newGlobal();
        global.forEach((globalName, globalType) -> globalDescrBuilder.identifier(globalName).type(globalType));
        return globalDescrBuilder.end();
    }

    /**
     * 设置规则头信息
     * @param packageName
     * @param ruleName
     * @param group
     * @param orderNo  越大执行越靠前
     * @return
     */
    public RuleDescrBuilder addRuleInfo(String packageName, String ruleName, String group, String orderNo) {
        return packageDescrBuilder.name(packageName).newRule().name(ruleName).attribute("agenda-group", "\"" + group + "\"").attribute("auto-focus", "").attribute("salience", orderNo).attribute("dialect", "java").attribute("no-loop", "true");
    }


    /**
     * 创建规则开头
     *
     * @param ruleDescrBuilder
     * @return
     */
    public CEDescrBuilder<RuleDescrBuilder, AndDescr> addRuleHander(RuleDescrBuilder ruleDescrBuilder) {
        return ruleDescrBuilder.lhs();
    }

    /**
     * 简易规则头
     *
     * @param packageName
     * @param ruleName
     * @param global
     * @return
     */
    public CEDescrBuilder<RuleDescrBuilder, AndDescr> simpleHander(String packageName, String ruleName, Map<String, String> global, String group, String orderNo) {
        if (null != global && global.size() > 0) {
            addGlobal(global);
        }
        return addRuleHander(addRuleInfo(packageName, ruleName, group, orderNo));
    }

    public HanderTemplate(String packageName, String ruleName, Map<String, String> global, String group, String orderNo) {
        ceDescrBuilder = simpleHander(packageName, ruleName, global, group, orderNo);
    }

    public CEDescrBuilder<RuleDescrBuilder, AndDescr> getCeDescrBuilder() {
        return ceDescrBuilder;
    }


}
