package com.server.QueryTest;

import com.server.MessageCommand.KnowledgeMessage;
import com.server.project.wms4.OdoCommand;
import com.server.command.RuleCommand;
import com.server.project.wms4.WhOdoLineCommand;
import com.server.manager.knowledge.KnowLedgeBaseManger;
import com.server.model.*;
import com.server.manager.query.QueryManager;
import com.server.manager.rule.RuleLoadManager;
import com.server.tools.FactLoader;
import com.server.utility.TestNgBase;
import com.server.utility.template.*;
import com.server.utility.template.condition.BaseConditionTemplate;
import com.server.utility.template.condition.EqualConditionTemplate;
import com.server.utility.template.condition.RangeConditionTemplate;
import org.drools.core.io.impl.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/24/18
 * Time: 9:54 AM
 */
public class ProcessTest extends TestNgBase {

    @Autowired
    private RuleLoadManager ruleLoadManager;

    @Autowired
    private FactLoader factLoader;

    @Autowired
    private KnowLedgeBaseManger knowLedgeBaseManger;

    @Autowired
    private QueryManager queryManager;

    public static final String PACKAGENAME = "com.server.project.wms4";

    /*public static final String PACKAGENAME = "com.server.command";*/

    public List<FactClassDescriptionInfo> loadTestFact() throws IOException {
        factLoader.setClassPath("/command/*.class");
        return factLoader.entityInfo();
    }


    @Test
    public void loadAllRuleForDB() {
        Map<String, List<BaseResource>> map = ruleLoadManager.loadAllRules();
    }

    @Test
    public void loadRuleForDB() {
        List<BaseResource> rule = ruleLoadManager.loadRule("测试一组");
        System.out.println(rule.size());
    }

    @Test
    public void checkRule() {
        Map<String, List<BaseResource>> map = ruleLoadManager.loadAllRules();

        map.forEach((group, resourceList) -> {
            KnowledgeMessage error = knowLedgeBaseManger.testRule(resourceList);
            System.out.println("--------------------" + error.getMessage());
        });
        List<BaseResource> rule = ruleLoadManager.loadRule("测试一组");
        KnowledgeMessage errort = knowLedgeBaseManger.testRule(rule);
        System.out.println("--------------------" + errort.getMessage());
    }


    @Test
    public void loadRuleToKnowLedgeBaseManger() {
        Map<String, List<BaseResource>> map = ruleLoadManager.loadAllRules();
        map.forEach((group, resourceList) -> {
            knowLedgeBaseManger.createKnowledgeBase(resourceList, group);
        });


        System.out.println("======================================================");
        List<OdoCommand> odoCommands = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            odoCommands.add(dataBuilders(i));
        }

        System.out.println("======================================================");

        long start = System.currentTimeMillis();
        //queryManager.queryCommandWithKieSessionAsList("测试一组", odoCommands);
        queryManager.queryCommandWithStatelessKieSessionAsList("testCode", odoCommands);
        System.out.println("=============================" + (System.currentTimeMillis() - start));
        /*int count=0;
        for (OdoCommand odoCommand : odoCommands) {
            System.out.println(odoCommand.getCustomerId() + "    " + odoCommand.getOdoIndex() + "          " + odoCommand.getOdoType() + "    " + odoCommand.getMessage());
            for (WhOdoLineCommand whOdoLineCommand : odoCommand.getWhOdoLineCommands()) {
                System.out.println("             --------------------" + whOdoLineCommand.getSkuBarCode());
                System.out.println("             --------------------" + whOdoLineCommand.getQty());
            }
            if(null!=odoCommand.getMessage()){
                count++;
            }
        }
        System.out.println(count);*/
        int count=0;
        for (int i = 0; i < odoCommands.size(); i++) {
            if ("111111".equals(odoCommands.get(i).getMessage()))
                count++;
        }
        System.out.println(count);

        System.out.println("======================================================");

    }

    public static OdoCommand dataBuilders(int i) {
        String t = "" + i;
        OdoCommand odoCommand = new OdoCommand();
        odoCommand.setAmt(new Double(1));
        odoCommand.setOuId(Long.valueOf(t));
        odoCommand.setOdoType(String.valueOf(new Random().nextInt(5)));
        odoCommand.setCustomerId(Long.valueOf(new Random().nextInt(2)));
        odoCommand.setOdoIndex(String.valueOf(new Random().nextInt(2)));
        odoCommand.setActualQty(Double.parseDouble(String.valueOf(new Random().nextInt(10))));
        List<WhOdoLineCommand> whOdoLineCommands = new ArrayList<WhOdoLineCommand>();
        WhOdoLineCommand whOdoLineCommand1 = new WhOdoLineCommand();
        whOdoLineCommand1.setQty(Double.parseDouble(t));
        whOdoLineCommand1.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand1.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand1);
        WhOdoLineCommand whOdoLineCommand2 = new WhOdoLineCommand();
        whOdoLineCommand2.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand2.setQty(Double.parseDouble(t));
        whOdoLineCommand2.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand2);
        WhOdoLineCommand whOdoLineCommand3 = new WhOdoLineCommand();
        whOdoLineCommand3.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand3.setQty(Double.parseDouble(t));
        whOdoLineCommand3.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand3);
        WhOdoLineCommand whOdoLineCommand4 = new WhOdoLineCommand();
        whOdoLineCommand4.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand4.setQty(Double.parseDouble(t));
        whOdoLineCommand4.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand4);
        WhOdoLineCommand whOdoLineCommand5 = new WhOdoLineCommand();
        whOdoLineCommand5.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand5.setQty(Double.parseDouble(t));
        whOdoLineCommand5.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand5);

        odoCommand.setWhOdoLineCommands(whOdoLineCommands);
        return odoCommand;
    }


    public static OdoCommand dataBuilder(int i) {
        String t = "" + i;
        OdoCommand odoCommand = new OdoCommand();
        odoCommand.setAmt(new Double(1));
        odoCommand.setOuId(Long.valueOf(t));
        odoCommand.setOdoType(String.valueOf(new Random().nextInt(5)));
        odoCommand.setCustomerId(Long.valueOf(new Random().nextInt(2)));
        odoCommand.setOdoIndex(String.valueOf(new Random().nextInt(2)));
        odoCommand.setActualQty(Double.parseDouble(String.valueOf(new Random().nextInt(10))));
        List<WhOdoLineCommand> whOdoLineCommands = new ArrayList<>();
        WhOdoLineCommand whOdoLineCommand1 = new WhOdoLineCommand();
        whOdoLineCommand1.setQty(Double.parseDouble(t));
        whOdoLineCommand1.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand1.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand1);
        //if (i % 2 > 0) {
            WhOdoLineCommand whOdoLineCommand2 = new WhOdoLineCommand();
            whOdoLineCommand2.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
            whOdoLineCommand2.setQty(Double.parseDouble(t));
            whOdoLineCommand2.setActualQty(new Random(i).nextDouble());
            whOdoLineCommands.add(whOdoLineCommand2);
        //}
        odoCommand.setWhOdoLineCommands(whOdoLineCommands);
        return odoCommand;
    }

}
