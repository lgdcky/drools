package com.baozun.netty.client.send;

import com.alibaba.fastjson.JSONObject;
import com.baozun.netty.client.RPCClient;
import com.baozun.netty.client.command.RuleCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

    private static final JSONObject jsonObject = new JSONObject();

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
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
        String json = jsonObject.toJSONString(map);
        System.out.println(formatter.format(new Date()) + "   to string");
        String json1 = jsonObject.toJSONString(map);
        System.out.println(formatter.format(new Date()) + "   to string");
        byte[] bytes = json.getBytes("UTF-8");
        System.out.println(formatter.format(new Date()) + "   to bytes");
        ChannelBuffer dynamicDuffer = ChannelBuffers.dynamicBuffer();
        dynamicDuffer.writeBytes(bytes);
        System.out.println(formatter.format(new Date()) + "   ready");
        ChannelFuture channelFuture = channel.write(dynamicDuffer);
        dynamicDuffer.clear();
        return channelFuture;
    }

}
