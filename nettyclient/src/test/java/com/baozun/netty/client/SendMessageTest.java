package com.baozun.netty.client;

import com.baozun.netty.client.command.RuleCommand;
import com.server.project.wms4.OdoCommand;
import com.baozun.netty.client.send.SendMessage;
import com.server.project.wms4.WhOdoLineCommand;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.baozun.netty.client.RpcClientTest.dataBuilder;
import static com.baozun.netty.client.RpcClientTest.initClient;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/6/18
 * Time: 7:11 PM
 */
public class SendMessageTest {

    /*public static void main(String[] args) throws Exception {
        sendTest();
    }*/

    private RPCClient rpcClient = initClient();

    @Test(threadPoolSize = 1, invocationCount = 1)
    private void sendTest() throws Exception {
        /*SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");*/

        List<OdoCommand> odoCommands = new ArrayList<OdoCommand>();
        for (int i = 0; i < 100000; i++) {
            odoCommands.add(dataBuilder(i));
        }
        //System.out.println(formatter.format(new Date()) + "   start");
       // long star = System.currentTimeMillis();
        RuleCommand<OdoCommand> ruleCommand = new RuleCommand<OdoCommand>();
        ruleCommand.setFactList(odoCommands);
        ruleCommand.setGroup("测试一组");
        ruleCommand.setType("odoCommandFact");

        SendMessage sendMessage = new SendMessage();
        sendMessage.setRpcClient(rpcClient);
        System.out.println(sendMessage.sendMessage(ruleCommand));
        //System.out.println(System.currentTimeMillis() - star + "   send message");
        sendMessage = null;
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

}
