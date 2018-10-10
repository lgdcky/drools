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

import static com.server.tools.DroolsConvertToResource.checkRuleId;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/16/18
 * Time: 9:41 AM
 * <p>
 * 后期可调优
 */
public class NestingCountTemplate {

    public static final String TYPE = "NESTINGCOUNT";

    private DescrBuilder descrBuilder;

    private String parentName;

    private String objName;

    private String attribute;

    private String calculation;

    private List<? extends BaseConditionTemplate> calConditionList;

    private List<? extends BaseConditionTemplate> conditionList;

    public NestingCountTemplate() {

    }

    public NestingCountTemplate(DescrBuilder descrBuilder) {
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

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public void setConditionList(List<? extends BaseConditionTemplate> conditionList) {
        this.conditionList = conditionList;
    }

    public void setCalConditionList(List<? extends BaseConditionTemplate> calConditionList) {
        this.calConditionList = calConditionList;
    }

    public DescrBuilder setNestingCountTemplate() {
        CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder = ((CEDescrBuilder<RuleDescrBuilder, AndDescr>) descrBuilder);
        String paramName = (((null == this.calculation ? "" : this.calculation.toLowerCase()) + Instant.now() + "Count").toString().replace("-", "").replace(":", "").replace(".", ""));
        String ruleId_p = "$" + this.parentName.toLowerCase();
        if (checkRuleId(ceDescrBuilder, ruleId_p)) {
            ceDescrBuilder.pattern().id("$" + this.parentName.toLowerCase(), true).type(parentName).end();
        }
        PatternDescrBuilder patternDescrBuilder = ceDescrBuilder
                .pattern().id("$" + paramName, true).type(List.class.getName());

        patternDescrBuilder = mergeCondition(patternDescrBuilder, calConditionList, "$" + paramName)
                .from().collect().pattern().id("$" + objName.toLowerCase(), true).type(objName);
        patternDescrBuilder = mergeCondition(patternDescrBuilder, conditionList, null);

        patternDescrBuilder.from().expression("$" + this.parentName.toLowerCase() + "." + attribute).end();
        return patternDescrBuilder.end().end().end();
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
