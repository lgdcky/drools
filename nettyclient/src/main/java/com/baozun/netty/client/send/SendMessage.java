package com.baozun.netty.client.send;

import com.baozun.netty.client.RPCClient;
import com.baozun.netty.client.command.RuleCommand;
import com.baozun.netty.client.tools.TypeConvertTools;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.baozun.netty.client.tools.CompressTool.compresss;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/6/18
 * Time: 4:29 PM
 */
public class SendMessage<T> {

    private static final String QUERYALLSUCCESS = "SUCCESS";

    private static final String QUERYPARTLYSUCCESS = "PARTIALSUCCESS";

    private static final String QUERYFAILED = "FAILED";

    private static final Integer BUFFERSIZE = 3 * 1024 * 1024;

    private RPCClient rpcClient;

    public RPCClient getRpcClient() {
        return rpcClient;
    }

    public void setRpcClient(RPCClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    public String sendMessage(RuleCommand<T> message) throws Exception {
        List<Map<String, Object[]>> dataList = message.getRuleCommandList();
        Channel channel = this.rpcClient.getChannel();


        if (!channel.isConnected())
            throw new Exception("connection failed");

        ChannelFuture channelFuture = null;
        int failed = 0;
        for (Map<String, Object[]> map : dataList) {
            channelFuture = processMessage(map, channel);
        }

        channelFuture.awaitUninterruptibly();

        assert channelFuture.isDone();

        if (channelFuture.isCancelled()) {
            channelFuture.getChannel().close();
            return QUERYFAILED;
        } else if (!channelFuture.isSuccess()) {
            channelFuture.getCause().printStackTrace();
            return QUERYFAILED;
        } else {
            return QUERYALLSUCCESS;
        }

    }

    private static final ChannelFuture processMessage(Map<String, Object[]> map, Channel channel) throws IOException {
        byte[] bytes = compresss(TypeConvertTools.objToBytesByStream(map));
        ChannelBuffer dynamicDuffer = ChannelBuffers.dynamicBuffer(BUFFERSIZE);
        dynamicDuffer.writeBytes(bytes);
        ChannelFuture channelFuture = channel.write(dynamicDuffer);
        dynamicDuffer.clear();
        return channelFuture;
    }

}