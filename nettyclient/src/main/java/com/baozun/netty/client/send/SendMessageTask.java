package com.baozun.netty.client.send;

import com.baozun.netty.client.tools.CallableThreadTool;
import com.baozun.netty.client.tools.TypeConvertTools;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.baozun.netty.client.tools.CompressTool.compresss;
import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 10:16 AM
 */
public class SendMessageTask extends CallableThreadTool<ChannelFuture> {

    private static Logger logger = LoggerFactory.getLogger(SendMessageTask.class);

    private Map<String, Object[]> map;

    private Channel channel;

    SendMessageTask(Map<String, Object[]> map, Channel channel) {
        this.channel = channel;
        this.map = map;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public ChannelFuture call() throws Exception {
        byte[] bytes = compresss(TypeConvertTools.objToBytesByStream(map));
        waitForConnection(channel);
        ChannelBuffer dynamicDuffer = ChannelBuffers.dynamicBuffer(bytes.length);
        dynamicDuffer.writeBytes(bytes);
        ChannelFuture channelFuture = channel.write(dynamicDuffer);
        dynamicDuffer.clear();
        return channelFuture;
    }


    private static void waitForConnection(Channel channel) throws InterruptedException {
        boolean flag = true;
        int count = 0;
        while (!channel.isConnected()) {
            if (10 < count++) {
                throw new RuntimeException("connection wait time too long!");
            }
            sleep(5);
        }
    }
}
