package com.server.utility.template;

import com.server.utility.template.condition.BaseConditionTemplate;
import org.drools.compiler.lang.api.CEDescrBuilder;
import org.drools.compiler.lang.api.DescrBuilder;
import org.drools.compiler.lang.api.PatternDescrBuilder;
import org.drools.compiler.lang.api.RuleDescrBuilder;
import org.drools.compiler.lang.descr.AndDescr;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/17/18
 * Time: 3:01 PM
 *
 * 后期可调优
 */
public class NestingCalculationTemplate {

    public static final String TYPE = "NESTINGCALCULATION";

    private DescrBuilder descrBuilder;

    private String parentName;

    private String objName;

    private String calculation;

    private String attribute;

    private List<? extends BaseConditionTemplate> calConditionList;

    private List<? extends BaseConditionTemplate> conditionList;

    public NestingCalculationTemplate(){

    }

    public NestingCalculationTemplate(DescrBuilder descrBuilder) {
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

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setCalConditionList(List<? extends BaseConditionTemplate> calConditionList) {
        this.calConditionList = calConditionList;
    }

    public void setConditionList(List<? extends BaseConditionTemplate> conditionList) {
        this.conditionList = conditionList;
    }

    public DescrBuilder setNestingCalculationTemplate() {
        return getDescrBuilder(parentName, objName, calculation, attribute, calConditionList, conditionList);
    }

    private DescrBuilder getDescrBuilder(String parentName, String objName, String calculation, String attribute, List<? extends BaseConditionTemplate> calConditionList, List<? extends BaseConditionTemplate> conditionList) {
        String alias = (objName.toLowerCase() + Instant.now() + "Cal").toString().replace("-","").replace(":","").replace(".","");
        String aliasType = Double.class.getName();

        CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder = ((CEDescrBuilder<RuleDescrBuilder, AndDescr>) descrBuilder);
        ceDescrBuilder
                .pattern().id("$" + parentName.toLowerCase(), true).type(parentName).end();
        PatternDescrBuilder patternDescrBuilder = ceDescrBuilder
                .pattern().id("$" + alias, true)
                .type(aliasType);


        patternDescrBuilder = mergeCondition(patternDescrBuilder, calConditionList, "$"+alias).from()
                .accumulate().init("Double total=0.00;")
                .action("total +=" + "$" + objName.toLowerCase() + ".get" + calculation+"();")
                .result("total")
                .source()
                .pattern()
                .id("$" + objName.toLowerCase(), true)
                .type(objName);

        mergeCondition(patternDescrBuilder, conditionList, null).from().expression("$" + parentName.toLowerCase() + "." + attribute)
                .end()
                .end()
                .end();
        return patternDescrBuilder.end().end().end().end();
    }

    public static PatternDescrBuilder mergeCondition(PatternDescrBuilder patternDescrBuilder, List<? extends BaseConditionTemplate> conditionList, String alias) {
        List<String> rules = new ArrayList<>();
        for (int i = 0; i < conditionList.size(); i++) {
            BaseConditionTemplate baseConditionTemplate = conditionList.get(i);
            if (baseConditionTemplate.getLinkOp().equals(BaseConditionTemplate.AND)) {
                rules.add(baseConditionTemplate.getReturnRule(alias));
            } else {
                if (i > 0) {
                    rules.add(rules.get(i - 1) + " " + BaseConditionTemplate.OR + " " + baseConditionTemplate.getReturnRule(alias));
                    rules.remove(i - 1);
                } else {
                    rules.add(baseConditionTemplate.getReturnRule(alias));
                }
            }
        }

        for (String rule : rules) {
            patternDescrBuilder.constraint(rule);
        }
        return patternDescrBuilder;
    }

}
