package com.baozun.netty.client;

import com.baozun.netty.client.command.RuleCommand;
import com.server.project.wms4.OdoCommand;
import com.server.project.wms4.WhOdoLineCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/5/18
 * Time: 8:05 PM
 */
public class CommandTest {

    public static OdoCommand dataBuilder(int i) {
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
        if (i % 2 > 0) {
            WhOdoLineCommand whOdoLineCommand2 = new WhOdoLineCommand();
            whOdoLineCommand2.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
            whOdoLineCommand2.setQty(Double.parseDouble(t));
            whOdoLineCommand2.setActualQty(new Random(i).nextDouble());
            whOdoLineCommands.add(whOdoLineCommand2);
        }
        odoCommand.setWhOdoLineCommands(whOdoLineCommands);
        return odoCommand;
    }

    //fastjson初始化时间太长
    public static void main(String[] args) {
        List<OdoCommand> odoCommands = new ArrayList<OdoCommand>();
        for (int i = 0; i < 200000; i++) {
            odoCommands.add(dataBuilder(i));
        }

        RuleCommand<OdoCommand> ruleCommand = new RuleCommand<OdoCommand>();
        ruleCommand.setFactList(odoCommands);
        ruleCommand.setGroup("test");
        ruleCommand.setType("odoCommand");
        List<Map<String,Object[]>> ruleCommands = null;
        try {
            ruleCommands = ruleCommand.getRuleCommandList();
            ruleCommands = ruleCommand.getRuleCommandList();
        } catch (Exception e) {
            e.printStackTrace();
        }

/*


        RuleCommand<OdoCommand> ruleCommandss = new RuleCommand<OdoCommand>();
        ruleCommandss.setFactList(odoCommands);
        ruleCommandss.setGroup("test");
        ruleCommandss.setType("odoCommand");
        List<Map<String,Object[]>> ruleCommandsss = null;
        try {
            long start = System.currentTimeMillis();
            ruleCommandsss = ruleCommandss.getRuleCommandList();
            System.out.println(System.currentTimeMillis()-start);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<OdoCommand> odoCommandsds = new ArrayList<OdoCommand>();
        for (int i = 200000; i >0; i--) {
            odoCommandsds.add(dataBuilder(i));
        }

        RuleCommand<OdoCommand> ruleCommandssss = new RuleCommand<OdoCommand>();
        ruleCommandssss.setFactList(odoCommandsds);
        ruleCommandssss.setGroup("test");
        ruleCommandssss.setType("odoCommand");
        List<Map<String,Object[]>> ruleCommandssssss = null;
        try {
            long start = System.currentTimeMillis();
            ruleCommandssssss = ruleCommandssss.getRuleCommandList();
            System.out.println(System.currentTimeMillis()-start);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/


    }

}
