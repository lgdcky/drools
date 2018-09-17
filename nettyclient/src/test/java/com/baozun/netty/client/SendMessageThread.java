package com.baozun.netty.client;

import com.baozun.netty.client.command.RuleCommand;
import com.baozun.netty.client.manager.MessageHandleManagerImpl;
import com.baozun.netty.client.property.NettyProperty;
import com.baozun.netty.client.send.SendMessage;
import com.baozun.netty.client.tools.ThreadTool;
import com.server.project.wms4.OdoCommand;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/11/18
 * Time: 6:17 PM
 */
public class SendMessageThread implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SendMessageThread.class);

    private List<OdoCommand> odoCommands;

    private ThreadTool threadTool;

    public SendMessageThread(List<OdoCommand> odoCommands,ThreadTool threadTool) {
        this.odoCommands = odoCommands;
        this.threadTool = threadTool;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        RuleCommand<OdoCommand> ruleCommand = new RuleCommand<OdoCommand>();
        ruleCommand.setFactList(odoCommands);
        ruleCommand.setGroup("测试一组");
        ruleCommand.setType("odoCommandFact");

        SendMessage sendMessage = new SendMessage();
        Channel channel = initClient().getChannel();
        sendMessage.setChannel(channel);
        sendMessage.setThreadTool(threadTool);
        try {
            sendMessage.sendMessage(ruleCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ChannelFuture initClient() {
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
        nettyProperty.setThreadMun(2);
        nettyProperty.setIp("127.0.0.1");

        NettyConfig config = new NettyConfig();
        config.setNettyProperty(nettyProperty);
        config.setMessageHandleManager(new MessageHandleManagerImpl());
        RPCClient rpcClient = new RPCClient();
        rpcClient.setNettyConfig(config);
        return rpcClient.start();
    }
}
