package com.server.command;

import com.server.utility.OpParameter;
import com.server.utility.RuleParameter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 6/22/18
 * Time: 5:24 PM
 */
public class RuleParameterCommand {

    private List<RuleParameter> ruleParameters;

    private List<OpParameter> opParameters;

    public List<RuleParameter> getRuleParameters() {
        return ruleParameters;
    }

    public void setRuleParameters(List<RuleParameter> ruleParameters) {
        this.ruleParameters = ruleParameters;
    }

    public List<OpParameter> getOpParameters() {
        return opParameters;
    }

    public void setOpParameters(List<OpParameter> opParameters) {
        this.opParameters = opParameters;
    }
}
