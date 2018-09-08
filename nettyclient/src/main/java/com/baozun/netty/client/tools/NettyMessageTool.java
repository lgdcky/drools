package com.baozun.netty.client.tools;

import org.jboss.netty.buffer.ByteBufferBackedChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import java.io.IOException;
import java.nio.ByteBuffer;

import static com.baozun.netty.client.tools.CompressTool.compresss;
import static com.baozun.netty.client.tools.CompressTool.uncompresss;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/7/18
 * Time: 2:14 PM
 */
public class NettyMessageTool {

    public static final String convertToString(ChannelHandlerContext ctx, MessageEvent e) throws IOException {
        Channel channel = ctx.getChannel();
        ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
        if (buffer.array().length <= 0) {
            return null;
        }
        String JsonString = new String(uncompresss(buffer.array()));
        return JsonString;
    }

    public static final ChannelFuture convertStringAndSend(ChannelHandlerContext ctx, MessageEvent e, String info) throws IOException {
        ChannelBuffer bufferByte = new ByteBufferBackedChannelBuffer(ByteBuffer.wrap(compresss(info.getBytes())));
        ChannelFuture channelFuture =  ctx.getChannel().write(bufferByte);
        bufferByte.clear();
        return channelFuture;
    }
}
