package com.baozun.netty.client.send;

import com.baozun.netty.client.NettyConfig;
import com.baozun.netty.client.RPCClient;
import com.baozun.netty.client.command.RuleCommand;
import com.baozun.netty.client.manager.MessageHandleManagerImpl;
import com.baozun.netty.client.property.NettyProperty;
import com.baozun.netty.client.tools.TypeConvertTools;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger logger = LoggerFactory.getLogger(SendMessage.class);


    private static final String QUERYALLSUCCESS = "SUCCESS";

    private static final String QUERYPARTLYSUCCESS = "PARTIALSUCCESS";

    private static final String QUERYFAILED = "FAILED";

    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String sendMessage(RuleCommand<T> message) throws Exception {
        List<Map<String, Object[]>> dataList = message.getRuleCommandList();
        int count=0;

        while (!channel.isConnected()) {
            logger.warn("connection failed");
            logger.warn("链接绑定状态 "+channel.isBound()+"   "+count++);
        }

        logger.warn("链接绑定状态 "+channel.isBound()+"  bound  "+count++);

        ChannelFuture channelFuture = null;
        int failed = 0;
        for (Map<String, Object[]> map : dataList) {
            channelFuture = processMessage(map, channel);
            Thread.sleep(100);
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

    private synchronized ChannelFuture processMessage(Map<String, Object[]> map, Channel channel) throws IOException {
        byte[] bytes = compresss(TypeConvertTools.objToBytesByStream(map));
        ChannelBuffer dynamicDuffer = ChannelBuffers.dynamicBuffer(bytes.length);
        dynamicDuffer.writeBytes(bytes);
        ChannelFuture channelFuture = channel.write(dynamicDuffer);
        dynamicDuffer.clear();
        return channelFuture;
    }

}
