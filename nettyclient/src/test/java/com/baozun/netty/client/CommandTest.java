package com.baozun.netty.client;

import com.baozun.netty.client.command.OdoCommand;
import com.baozun.netty.client.command.RuleCommand;
import com.baozun.netty.client.command.WhOdoLineCommand;
import com.baozun.netty.client.property.NettyProperty;

import java.util.ArrayList;
import java.util.List;
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
        List<WhOdoLineCommand> whOdoLineCommands = new ArrayList<>();
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

    public static void main(String[] args) {
        List<OdoCommand> odoCommands = new ArrayList<>();
        for (int i = 0; i < 500000; i++) {
            odoCommands.add(dataBuilder(i));
        }

        RuleCommand<OdoCommand> ruleCommand = new RuleCommand<>();
        ruleCommand.setFactList(odoCommands);
        ruleCommand.setGroup("test");
        ruleCommand.setType("odoCommand");
        List<RuleCommand> ruleCommands = ruleCommand.getRuleCommandList();
        System.out.println(ruleCommands.size());
    }

}
