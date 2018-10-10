package com.server.utility.template;

import com.server.tools.DroolsConvertToResource;
import com.server.utility.template.condition.BaseConditionTemplate;
import org.drools.compiler.lang.api.CEDescrBuilder;
import org.drools.compiler.lang.api.DescrBuilder;
import org.drools.compiler.lang.api.PatternDescrBuilder;
import org.drools.compiler.lang.api.RuleDescrBuilder;
import org.drools.compiler.lang.descr.AndDescr;
import org.drools.compiler.lang.descr.PatternDescr;

import java.util.ArrayList;
import java.util.List;

import static com.server.tools.DroolsConvertToResource.checkRuleId;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/15/18
 * Time: 3:15 PM
 */
public class SimpleRuleTemplate {

    public static final String TYPE = "SIMPLERULE";

    private DescrBuilder descrBuilder;

    private String objName;

    private List<? extends BaseConditionTemplate> conditionList;

    public SimpleRuleTemplate() {

    }

    public SimpleRuleTemplate(DescrBuilder descrBuilder) {
        this.descrBuilder = descrBuilder;
    }

    public void setDescrBuilder(DescrBuilder descrBuilder) {
        this.descrBuilder = descrBuilder;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public void setConditionList(List<? extends BaseConditionTemplate> conditionList) {
        this.conditionList = conditionList;
    }

    public DescrBuilder setSimpleRuleTemplate() {
        CEDescrBuilder<RuleDescrBuilder, AndDescr> ceDescrBuilder = ((CEDescrBuilder<RuleDescrBuilder, AndDescr>) descrBuilder);
        String ruleId = "$" + this.objName.toLowerCase();
        PatternDescrBuilder patternDescrBuilder = null;
        patternDescrBuilder = ceDescrBuilder.pattern().id(ruleId, true).type(objName);
        List<String> rules = new ArrayList<>();
        for (int i = 0; i < conditionList.size(); i++) {
            BaseConditionTemplate baseConditionTemplate = conditionList.get(i);
            if (baseConditionTemplate.getLinkOp().equals(BaseConditionTemplate.AND)) {
                rules.add(baseConditionTemplate.getReturnRule(null));
            } else {
                if (i > 0) {
                    rules.add(rules.get(i - 1) + " " + BaseConditionTemplate.OR + " " + baseConditionTemplate.getReturnRule(null));
                    rules.remove(i - 1);
                } else {
                    rules.add(baseConditionTemplate.getReturnRule(null));
                }
            }
        }

        for (String rule : rules) {
            patternDescrBuilder.constraint(rule);
        }


        return patternDescrBuilder.end();
    }

}
