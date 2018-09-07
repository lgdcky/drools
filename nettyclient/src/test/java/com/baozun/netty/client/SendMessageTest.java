package com.baozun.netty.client;

import com.baozun.netty.client.command.OdoCommand;
import com.baozun.netty.client.command.RuleCommand;
import com.baozun.netty.client.send.SendMessage;
import com.baozun.netty.client.tools.CompressTool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.baozun.netty.client.CommandTest.dataBuilder;
import static com.baozun.netty.client.RpcClientTest.initClient;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/6/18
 * Time: 7:11 PM
 */
public class SendMessageTest{

    public static void main(String[] args) throws Exception {
        RPCClient rpcClient = initClient();

        List<OdoCommand> odoCommands = new ArrayList<OdoCommand>();
        for (int i = 0; i < 100000; i++) {
            odoCommands.add(dataBuilder(i));
        }

        RuleCommand<OdoCommand> ruleCommand = new RuleCommand<OdoCommand>();
        ruleCommand.setFactList(odoCommands);
        ruleCommand.setGroup("test");
        ruleCommand.setType("odoCommand");

        SendMessage sendMessage = new SendMessage();
        sendMessage.setRpcClient(rpcClient);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
        System.out.println(formatter.format(new Date()) + "   start");
        System.out.println(sendMessage.sendMessage(ruleCommand));

        SendMessage sendMessage1 = new SendMessage();
        sendMessage1.setRpcClient(rpcClient);
        formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
        System.out.println(formatter.format(new Date()) + "   start");
        System.out.println(sendMessage1.sendMessage(ruleCommand));
    }

}
