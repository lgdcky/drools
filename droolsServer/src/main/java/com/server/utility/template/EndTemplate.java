package com.server.utility.template;

import org.drools.compiler.lang.DrlDumper;
import org.drools.compiler.lang.api.DescrBuilder;
import org.drools.compiler.lang.api.PackageDescrBuilder;
import org.drools.compiler.lang.api.RuleDescrBuilder;
import org.drools.core.io.impl.ByteArrayResource;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/15/18
 * Time: 1:56 PM
 */
public class EndTemplate {

    private PackageDescrBuilder packageDescrBuilder;

    /**
     * 结尾构建器
     * @param descrBuilder
     * @param handle
     * @return
     */
    public EndTemplate(DescrBuilder descrBuilder, String handle){
        packageDescrBuilder = ((RuleDescrBuilder)descrBuilder.end()).rhs(handle).end();
    }

    /**
     * 返回规则源
     * @return
     */
    public ByteArrayResource returnRuleString(){
        String drl = new DrlDumper().dump(packageDescrBuilder.getDescr());
        return new ByteArrayResource(drl.getBytes(), "UTF-8");
    }

}
