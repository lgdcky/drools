package com.server.QueryTest;

import com.server.knowledgebasemanager.KnowLedgeBaseManger;
import com.server.command.OdoCommand;
import com.server.command.WhOdoLineCommand;
import com.server.manager.query.QueryManager;
import com.server.utility.TestNgBase;
import org.drools.compiler.lang.DrlDumper;
import org.drools.compiler.lang.api.DescrFactory;
import org.drools.compiler.lang.api.PackageDescrBuilder;
import org.drools.core.io.impl.BaseResource;
import org.drools.core.io.impl.ByteArrayResource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/27/18
 * Time: 11:40 AM
 */
public class QueryTest extends TestNgBase {

    @Autowired
    private KnowLedgeBaseManger knowLedgeBaseManger;

    @Autowired
    private QueryManager queryManager;

    @Test
    public void queryTestWithList() {
        createKnowLegdeBase();

        List<OdoCommand> odoCommands = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            odoCommands.add(retrueOdo1(i));
        }

        long start = System.currentTimeMillis();
        List list = (List) queryManager.queryCommandWithStatelessKieSessionAsList("test", odoCommands);
        System.out.println("=============================" + (System.currentTimeMillis() - start));
        for (OdoCommand odoCommand : odoCommands) {
            System.out.println(odoCommand.getOdoType() + "    " + odoCommand.getCustomerId() + "    " + odoCommand.getOdoIndex() + "    " + odoCommand.getMessage());
            for (WhOdoLineCommand whOdoLineCommand : odoCommand.getWhOdoLineCommands()) {
                System.out.println("             --------------------" + whOdoLineCommand.getSkuBarCode());
            }
        }

        /*for (OdoCommand odoCommand : odoCommands) {
            System.out.println(odoCommand.getOuId()+"=================================");
            System.out.println(odoCommand.getQty());
            for (WhOdoLineCommand whOdoLineCommand : odoCommand.getWhOdoLineCommands()) {
                System.out.println(whOdoLineCommand.getActualQty());
                System.out.println(whOdoLineCommand.getSkuBarCode());
            }
        }*/
    }

    public ByteArrayResource createOdoLineRule() {
        PackageDescrBuilder packBuilder;
        packBuilder = DescrFactory.newPackage().newGlobal().identifier("list").type(List.class.getName()).end()
                .name("com.server.command").newRule().name("test")
                .lhs()
                .pattern().id("$odoCommand", true).type(OdoCommand.class.getName())
                .constraint("odoType in (\"1\",\"2\",\"3\",\"4\")")//.constraint("customerId==1").constraint("odoIndex==\"0\"")
                .end()


                //计算对象内部list符合条件的某列的和
                .pattern()
                .id("$testSum", true)
                .type(Double.class.getName()).constraint("$testSum>10")
                .from()
                .accumulate()
                .init("Double total=0.00;")
                .action("total+=$line.getQty();")
                .result("total")
                .source()
                .pattern()
                .id("$line", true)
                .type(WhOdoLineCommand.class.getName())
                .constraint("skuBarCode == \"192283108137\"")
                .from()
                .expression("$odoCommand.whOdoLineCommands")
                .end()
                .end()
                .end()
                .end()


                //计算对象内部list符合条件的数量
                /*.pattern().id("$line",true).type(List.class.getName())
                .from().collect().pattern().id("$whOdoLineCommand",true).type(WhOdoLineCommand.class.getName())
                .constraint("skuBarCode == \"192283108137\"")
                .from().expression("$odoCommand.whOdoLineCommands")
                .end()
                .end()
                .end()*/


                //嵌套内判断
                /*.exists().and()
                .pattern().id("$whOdoLineCommand", true).type(WhOdoLineCommand.class.getName())
                .constraint("skuBarCode == \"192283108137\"")
                .from().expression("$odoCommand.whOdoLineCommands")
                .end()
                .end()
                .end()*/
                .end()
                //.rhs("System.out.println($testSum+\"========================\"+$odoCommand.getOuId());$odoCommand.setQty($testSum);").end();
                .rhs("$odoCommand.setMessage(\"2\");System.out.println($odoCommand.getOdoType()+\"++++++++++++++++\"+$odoCommand.getOuId()+\"++++++++++++++++\"+$testSum);").end();
        String drl = new DrlDumper().dump(packBuilder.getDescr());
        return new ByteArrayResource(drl.getBytes(), "UTF-8");
    }

    public ByteArrayResource createOdoRule() {
        PackageDescrBuilder packBuilder =
                DescrFactory.newPackage().newGlobal().identifier("list").type(List.class.getName()).end()
                        .name("com.server.command").newRule().name("test")
                        .lhs()
                        .pattern().id("$odoCommand", true).type(OdoCommand.class.getName())
                        .constraint("odoType in (1,2,3,4)").constraint("customerId==1").end()//.constraint("odoIndex==0").end()
                        .end()
                        .rhs("$odoCommand.setMessage(\"1\");").end();
        packBuilder.newRule().name("test1").lhs()
                .pattern().id("$odoCommand", true).type(OdoCommand.class.getName())
                .constraint("odoType==1").end()
                .end()
                .rhs("$odoCommand.setMessage(\"2\");").end();
        String drl = new DrlDumper().dump(packBuilder.getDescr());
        return new ByteArrayResource(drl.getBytes(), "UTF-8");
    }

    public static OdoCommand retrueOdo1(int i) {
        String t = "" + i;
        OdoCommand odoCommand = new OdoCommand();
        odoCommand.setOuId(Long.valueOf(t));
        odoCommand.setOdoType(String.valueOf(new Random().nextInt(5)));
        odoCommand.setCustomerId(Long.valueOf(new Random().nextInt(2)));
        odoCommand.setOdoIndex(String.valueOf(new Random().nextInt(2)));
        odoCommand.setActualQty(Double.parseDouble(String.valueOf(new Random().nextInt(10))));
        List<WhOdoLineCommand> whOdoLineCommands = new ArrayList<>();
        WhOdoLineCommand whOdoLineCommand1 = new WhOdoLineCommand();
        whOdoLineCommand1.setQty(Double.parseDouble(i + ""));
        whOdoLineCommand1.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand1.setActualQty(new Random(i).nextDouble());
        WhOdoLineCommand whOdoLineCommand2 = new WhOdoLineCommand();
        whOdoLineCommand2.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand2.setQty(Double.parseDouble(i + ""));
        whOdoLineCommand2.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand1);
        whOdoLineCommands.add(whOdoLineCommand2);
        odoCommand.setWhOdoLineCommands(whOdoLineCommands);
        return odoCommand;
    }

    public static OdoCommand retrueOdo(int i) {
        String t = "" + i;
        OdoCommand odoCommand = new OdoCommand();
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
        whOdoLineCommand2.setQty(Double.parseDouble(t) + 1);
        whOdoLineCommand2.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand1);
        whOdoLineCommands.add(whOdoLineCommand2);
        odoCommand.setWhOdoLineCommands(whOdoLineCommands);
        return odoCommand;
    }

    public void createKnowLegdeBase() {
        List<BaseResource> list = new ArrayList<>();
        list.add(createOdoLineRule());
        knowLedgeBaseManger.createKnowledgeBase(list, "test");
    }

    public ByteArrayResource createRuleAsList() {
        /*PackageDescrBuilder packBuilder =
                DescrFactory.newPackage().newGlobal().identifier("list").type(List.class.getName()).end()
                        .name("com.server.command")
                        .newRule().name("test")
                        .lhs()
                        .pattern().id("$stockTick", true).type(StockTickT.class.getName()).end()
                        .pattern().id("$stockTick", true).type(StockTickT.class.getName()).constraint("price>100").end()
                        .end()
                        .rhs("list.add( $stockTick );")
                        .end();
        packBuilder.newQuery().name("query-1").lhs()
                .pattern().id("$stockTick", true).type(StockTickT.class.getName()).constraint("price>100").end();
        String drl = new DrlDumper().dump(packBuilder.getDescr());*/
        return new ByteArrayResource("".getBytes(), "UTF-8");
    }

    @Test
    public void queryTest() {
        /*createKnowLegdeBase();
        StockTickT stockTickT1 = new StockTickT();
        stockTickT1.setPrice(200);
        stockTickT1.setCompany("RHT");
        StockTickT stockTickT2 = new StockTickT();
        stockTickT2.setPrice(10);
        stockTickT2.setCompany("RHT");
        StockTickT stockTickT3 = new StockTickT();
        stockTickT3.setPrice(101);
        stockTickT3.setCompany("RHT");
        List<StockTickT> listSt = new ArrayList<>();
        listSt.add(stockTickT1);
        listSt.add(stockTickT2);
        listSt.add(stockTickT3);
        Map<String, Map<String, List<String>>> map = new HashMap<>();
        Map<String, List<String>> mapQuery = new HashMap<>();
        //List<String> args = new ArrayList<>();
        //mapQuery.put("query-1", args);
        // map.put("test", mapQuery);
        ExecutionResults executionResults = (ExecutionResults) queryManager.queryCommandWithStatelessKieSession("test", map, listSt);*/

    }

    public ByteArrayResource createRuleTest() {
        /*PackageDescrBuilder packBuilder =
                DescrFactory.newPackage().newGlobal().identifier("list").type(List.class.getName()).end()
                        .name("com.server.command")
                        .newRule().name("test")
                        .lhs()
                        .pattern().id("$stockTick", true).type(StockTickT.class.getName()).end()
                        .pattern().id("$stockTick", true).type(StockTickT.class.getName()).constraint("price>100").end()
                        .end()
                        .rhs("    System.out.println(\"foo===================================\");\n")
                        .end();
        packBuilder.newQuery().name("query-1").lhs()
                .pattern().id("$stockTick", true).type(StockTickT.class.getName()).constraint("price>100").end();
        String drl = new DrlDumper().dump(packBuilder.getDescr());*/
        return new ByteArrayResource("".getBytes(), "UTF-8");
    }

}
