package com.baozun.netty.client;

import com.baozun.netty.client.command.RuleCommand;
import com.baozun.netty.client.manager.MessageHandleManagerImpl;
import com.baozun.netty.client.property.NettyProperty;
import com.baozun.netty.client.send.SendMessage;
import com.baozun.netty.client.tools.ThreadTool;
import com.server.project.wms4.OdoCommand;
import com.server.project.wms4.WhOdoLineCommand;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SendMessageThreadTest {

    private static Logger logger = LoggerFactory.getLogger(SendMessageThreadTest.class);

    public static void main(String[] args) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
        System.out.println(formatter.format(new Date()) + "   start");
        ThreadTool threadTool = new ThreadTool();
        SendMessageThreadTest sendMessageThreadTest = new SendMessageThreadTest();
        sendMessageThreadTest.sendTest(threadTool);
    }

    private void sendTest(ThreadTool threadTool) throws Exception {
        List<OdoCommand> odoCommands = new ArrayList<OdoCommand>();
        for (int i = 0; i < 10000; i++) {
            odoCommands.add(dataBuilder(i));
        }

        for(int i=0;i<10;i++){
            Thread thread = new Thread(new SendMessageThread(odoCommands,threadTool));
            thread.run();
        }
    }

    public static ChannelFuture initClient() {
        NettyProperty nettyProperty = new NettyProperty();
        nettyProperty.setIdleReadTime(20);
        nettyProperty.setIdleTime(20);
        nettyProperty.setIdleWriteTime(20);
        nettyProperty.setInitialBytesToStrip(4);
        nettyProperty.setLengthAdjustment(0);
        nettyProperty.setLengthFieldLength(4);
        nettyProperty.setLengthFieldOffset(0);
        nettyProperty.setMaxFrameLength(Integer.MAX_VALUE);
        nettyProperty.setPort(8092);
        nettyProperty.setReadTime(20);
        nettyProperty.setWriteTime(20);
        nettyProperty.setIp("127.0.0.1");

        NettyConfig config = new NettyConfig();
        config.setNettyProperty(nettyProperty);
        config.setMessageHandleManager(new MessageHandleManagerImpl());
        RPCClient rpcClient = new RPCClient();
        rpcClient.setNettyConfig(config);
        return rpcClient.start();
    }

    public void send(List<OdoCommand> odoCommands) {
        RuleCommand<OdoCommand> ruleCommand = new RuleCommand<OdoCommand>();
        ruleCommand.setFactList(odoCommands);
        ruleCommand.setGroup("测试一组");
        ruleCommand.setType("odoCommandFact");

        SendMessage sendMessage = new SendMessage();
        Channel channel = initClient().getChannel();
        System.out.println(channel.getClass().hashCode());
        sendMessage.setChannel(channel);
        try {
            sendMessage.sendMessage(ruleCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
