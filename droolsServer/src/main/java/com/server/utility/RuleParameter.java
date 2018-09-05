package com.server.utility;

import com.server.utility.template.condition.BaseConditionTemplate;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/20/18
 * Time: 10:11 PM
 */
@Validated
public class RuleParameter {

    public static final String SIMPLE = "simple";

    public static final String NESTCAL = "nestcal";

    public static final String NES = "nes";

    public static final String NESCOUNT = "nescount";

    @NotNull
    private String type;

    private String objName;

    private List<? extends BaseConditionTemplate> conditionList;

    private List<? extends BaseConditionTemplate> calConditionList;

    private String parentName;

    private String attribute;

    private String calculation;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public List<? extends BaseConditionTemplate> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<? extends BaseConditionTemplate> conditionList) {
        this.conditionList = conditionList;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public List<? extends BaseConditionTemplate> getCalConditionList() {
        return calConditionList;
    }

    public void setCalConditionList(List<? extends BaseConditionTemplate> calConditionList) {
        this.calConditionList = calConditionList;
    }
}
