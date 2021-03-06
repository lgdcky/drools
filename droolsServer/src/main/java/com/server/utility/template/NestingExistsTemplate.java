package com.server.utility.template;

import com.server.utility.template.condition.BaseConditionTemplate;
import org.drools.compiler.lang.api.CEDescrBuilder;
import org.drools.compiler.lang.api.DescrBuilder;
import org.drools.compiler.lang.api.PatternDescrBuilder;
import org.drools.compiler.lang.api.RuleDescrBuilder;
import org.drools.compiler.lang.descr.AndDescr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/16/18
 * Time: 9:41 AM
 *
 * 后期可调优
 */
public class NestingExistsTemplate {

    public static final String TYPE = "NESTINGEXISTS";

    private DescrBuilder descrBuilder;

    private String parentName;

    private String objName;

    private String attribute;

    private List<? extends BaseConditionTemplate> conditionList;

    public NestingExistsTemplate(){

    }

    public NestingExistsTemplate(DescrBuilder descrBuilder) {
        this.descrBuilder = descrBuilder;
    }

    public void setDescrBuilder(DescrBuilder descrBuilder) {
        this.descrBuilder = descrBuilder;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setConditionList(List<? extends BaseConditionTemplate> conditionList) {
        this.conditionList = conditionList;
    }

    public DescrBuilder setNestingExistsTemplate() {
        CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder = ((CEDescrBuilder<RuleDescrBuilder, AndDescr>) descrBuilder);
        ceDescrBuilder.pattern().id("$" + this.parentName.toLowerCase(), true).type(parentName).end();
        PatternDescrBuilder patternDescrBuilder = ceDescrBuilder
                .exists().and().pattern().id("$" + this.objName.toLowerCase(), true).type(objName);

        List<String> rules = new ArrayList<>();
        for(int i=0;i<conditionList.size();i++){
            BaseConditionTemplate baseConditionTemplate = conditionList.get(i);
            if(baseConditionTemplate.getLinkOp().equals(BaseConditionTemplate.AND)){
                rules.add(baseConditionTemplate.getReturnRule(null));
            }else{
                if(i>0) {
                    rules.add(rules.get(i-1)+" "+BaseConditionTemplate.OR+" "+baseConditionTemplate.getReturnRule(null));
                    rules.remove(i-1);
                }else{
                    rules.add(baseConditionTemplate.getReturnRule(null));
                }
            }
        }


        for(String rule:rules){
            patternDescrBuilder.constraint(rule);
        }

        patternDescrBuilder.from().expression("$" + this.parentName.toLowerCase() + "." + attribute).end();
        return patternDescrBuilder.end().end().end();
    }

}
