package com.server.utility;

import com.server.command.OdoCommand;
import com.server.command.WhOdoLineCommand;
import com.server.knowledgebasemanager.KnowLedgeBaseManger;
import com.server.manager.query.QueryManager;
import com.server.tools.FactLoader;
import com.server.utility.template.HanderTemplate;
import com.server.utility.template.SimpleRuleTemplate;
import com.server.utility.template.condition.BaseConditionTemplate;
import com.server.utility.template.condition.EqualConditionTemplate;
import com.server.utility.template.condition.RangeConditionTemplate;
import org.drools.core.io.impl.BaseResource;
import org.drools.core.io.impl.ByteArrayResource;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/15/18
 * Time: 5:09 PM
 */



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TemplateTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private KnowLedgeBaseManger knowLedgeBaseManger;

    @Autowired
    private QueryManager queryManager;


    public ByteArrayResource ruleTest() {
        Map<String, String> global = new HashMap<>();
        global.put("list", List.class.getName());
        HanderTemplate handerTemplate = new HanderTemplate("com.server.command", "test", global,null,null);
        SimpleRuleTemplate simpleRuleTemplate = new SimpleRuleTemplate(handerTemplate.getCeDescrBuilder());
        List<BaseConditionTemplate> conditionTemplates = new ArrayList<>();
        conditionTemplates.add(new EqualConditionTemplate("customerId", EqualConditionTemplate.EQ, "1",BaseConditionTemplate.AND));
        conditionTemplates.add(new EqualConditionTemplate("odoIndex", EqualConditionTemplate.EQ, "\"0\"",BaseConditionTemplate.OR));

        String rangeList = "\"1\",\"2\",\"3\",\"4\"";
        conditionTemplates.add(new RangeConditionTemplate("odoType", RangeConditionTemplate.IN, rangeList,BaseConditionTemplate.AND));

        List<BaseConditionTemplate> nestingConditionTemplates = new ArrayList<>();
        nestingConditionTemplates.add(new EqualConditionTemplate("qty", EqualConditionTemplate.EQ, "1",BaseConditionTemplate.AND));

        List<BaseConditionTemplate> nestingConditionTemplatesCal = new ArrayList<>();
        nestingConditionTemplates.add(new EqualConditionTemplate("skuBarCode", EqualConditionTemplate.EQ, "\"192283108137\"",BaseConditionTemplate.OR));

        //nestingConditionTemplates.add(new EqualConditionTemplate("qty", EqualConditionTemplate.EQ, "2",BaseConditionTemplate.OR));

        List<BaseConditionTemplate> nestingCalConditionTemplates = new ArrayList<>();
        nestingCalConditionTemplates.add(new EqualConditionTemplate("",EqualConditionTemplate.GT,"10",BaseConditionTemplate.AND));

        //NestingExistsTemplate nestingExistsTemplate = new NestingExistsTemplate(simpleRuleTemplate.setSimpleRuleTemplate(OdoCommand.class.getName(), conditionTemplates));

        //NestingExistsTemplate nestingExistsTemplate = new NestingExistsTemplate(simpleRuleTemplate.setSimpleRuleTemplate(OdoCommand.class.getName(), conditionTemplates));

        //NestingExistsTemplate nestingExistsTemplate = new NestingExistsTemplate(handerTemplate.getCeDescrBuilder());

        //StringTemplate stringTemplate = new StringTemplate(handerTemplate.getCeDescrBuilder());

        //EndTemplate endTemplate = new EndTemplate(simpleRuleTemplate.setSimpleRuleTemplate(OdoCommand.class.getName(), conditionTemplates), "$odocomman.setMessage(\"2\");");

        //EndTemplate endTemplate = new EndTemplate(nestingExistsTemplate.setNestingCountTemplate(OdoCommand.class.getName(), WhOdoLineCommand .class.getName(), "whOdoLineCommands", nestingConditionTemplates), "$odocomman.setMessage(\"2\");");
        //EndTemplate endTemplate = new EndTemplate(stringTemplate.setStringTemplate("aa"), "$odocomman.setMessage(\"2\");");

        //NestingCalculationTemplate nestingCalculationTemplate = new NestingCalculationTemplate(handerTemplate.getCeDescrBuilder());

        //NestingCalculationTemplate nestingCalculationTemplate = new NestingCalculationTemplate(nestingExistsTemplate.setNestingCountTemplate(OdoCommand.class.getName(), WhOdoLineCommand .class.getName(), "whOdoLineCommands", nestingConditionTemplates));


        //EndTemplate endTemplate = new EndTemplate(nestingCalculationTemplate.setNestingCountTemplate(OdoCommand.class.getName(),
          //      WhOdoLineCommand .class.getName(),"Qty","whOdoLineCommands",nestingCalConditionTemplates,nestingConditionTemplatesCal),"$odocomman.setMessage(\"2\");");
        return null;//endTemplate.returnRuleString();
    }

    public void createKnowLegdeBase() {
        List<BaseResource> list = new ArrayList<>();
        list.add(ruleTest());
        knowLedgeBaseManger.createKnowledgeBase(list, "test");
    }


    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    @BeforeClass
    public void beforeClass(){
        System.out.println("Start Time: " + df.format(new Date()));
    }

    @AfterClass
    public void afterClass(){
        System.out.println("End Time: " + df.format(new Date()));
    }

    @Test
    public void queryTestWithList() {
        createKnowLegdeBase();

        List<OdoCommand> odoCommands = new ArrayList<>();
        for (int i = 0; i < 500000; i++) {
            odoCommands.add(retrueOdo(i));
        }


        long start = System.currentTimeMillis();
        queryManager.queryCommandWithStatelessKieSessionAsList("test", odoCommands);
        //queryManager.queryCommandWithKieSessionAsList("test", odoCommands);

        System.out.println("=============================" + (System.currentTimeMillis() - start));
        /*for (OdoCommand odoCommand : odoCommands) {
                System.out.println(odoCommand.getOuId() + "    " +odoCommand.getOdoType() + "    " + odoCommand.getCustomerId() + "    " + odoCommand.getOdoIndex() + "    " + odoCommand.getMessage());
                for (WhOdoLineCommand whOdoLineCommand : odoCommand.getWhOdoLineCommands()) {
                    System.out.println("             --------------------" + whOdoLineCommand.getSkuBarCode());
                    System.out.println("             --------------------" + whOdoLineCommand.getQty());
                }
        }*/
        int count=0;
        for (OdoCommand odoCommand : odoCommands) {
            if (null != odoCommand.getMessage()) {
                count++;
            }
        }
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^"+count);
    }

    public static OdoCommand retrueOdo(int i) {
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
        WhOdoLineCommand whOdoLineCommand2 = new WhOdoLineCommand();
        whOdoLineCommand2.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand2.setQty(Double.parseDouble(t));
        whOdoLineCommand2.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand1);
        whOdoLineCommands.add(whOdoLineCommand2);
        odoCommand.setWhOdoLineCommands(whOdoLineCommands);
        return odoCommand;
    }

    @Autowired
    private KnowledgeBaseLib knowledgeBaseLib;

    public static final String drl = "package com.server.command \n" +
            "\n" +
            "global java.util.List list\n" +
            "\n" +
            "rule \"测试\"\n" +
            "    ruleflow-group \"tttt\"\n" +
            "    salience 1\n" +
            "    dialect \"java\"\n" +
            "when\n" +
            "    $odocommand := OdoCommand( customerId==1 || odoIndex==0, odoType in (1,2,3,4) )  \n" +
            "then\n" +
            "System.out.println(\"dddddddd\");\n" +
            "\n" +
            "end";

    @Test
    public void ttt(){
        KieServices kieServices = KieServices.Factory.get();
        final KieRepository kieRepository = kieServices.getRepository();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write( "src/main/resources/rule/rule.drl",
                kieServices.getResources().newByteArrayResource(drl.getBytes()));
        kieRepository.addKieModule(new KieModule() {
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });
        KieBuilder kieBuilder = kieServices.newKieBuilder( kfs ).buildAll();
        KieContainer kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
        KieSession kieSession = kieContainer.newKieSession();


        long start = System.currentTimeMillis();
        for (int i = 0; i <= 100; i++) {
            kieSession.insert(retrueOdo(i));
        }
        kieSession.fireAllRules();
        System.out.println("=============================" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        for (int i = 0; i <= 10000; i++) {
            kieSession.insert(retrueOdo(i));
        }
        kieSession.fireAllRules();
        System.out.println("=============================" + (System.currentTimeMillis() - start));
    }

    @Autowired
    private FactLoader entityLoader;

    @Test
    private void tt(){
        System.out.println(null==entityLoader);
    }

}
