package com.server.rpc;


import com.alibaba.fastjson.JSON;
import com.server.rpc.event.ExceptionHandler;
import com.server.tools.CompressTool;
import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.jboss.netty.buffer.ByteBufferBackedChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.server.tools.CompressTool.compresss;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/28/18
 * Time: 1:53 PM
 */

public class RPCServerHandler extends SimpleChannelHandler {

    private static Logger logger = LoggerFactory.getLogger(RPCServerHandler.class);

    private Integer bufferSize = 0;

    private List<Integer> list = new ArrayList<>();

    /**
     * Invoked when a message object (e.g: {@link ChannelBuffer}) was received
     * from a remote peer.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Channel channel = ctx.getChannel();
        ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
        if (buffer.array().length <= 0) {
            return;
        }
        System.out.println(buffer.array().length);
        String JsonString = new String(CompressTool.uncompresss(buffer.array()));
        ChannelBuffer bufferByte = new ByteBufferBackedChannelBuffer(ByteBuffer.wrap(compresss(JsonString.getBytes())));
        channel.write(buffer);
        System.out.println(channel.getId()+"    id");
        buffer.clear();
    }

    /**
     * Invoked when an exception was raised by an I/O thread or a
     * {@link ChannelHandler}.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        logger.error(e.toString());
        super.exceptionCaught(ctx, e);
        ExceptionHandler.exceptionHandle(ctx, e);
    }

    /**
     * Invoked when a {@link Channel} is open, bound to a local address, and
     * connected to a remote address.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        logger.debug("the new connection was opened");
        super.channelConnected(ctx, e);
    }

    /**
     * Invoked when a {@link Channel} was disconnected from its remote peer.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        logger.debug("the new connection was Disconnected");
        super.channelDisconnected(ctx, e);
    }

    /**
     * Invoked when a {@link Channel} was closed and all its related resources
     * were released.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        logger.debug("the new connection was Closed");
        super.channelClosed(ctx, e);
    }

    /**
     * {@inheritDoc}  Down-casts the received upstream event into more
     * meaningful sub-type event and calls an appropriate handler method with
     * the down-casted event.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof IdleStateEvent) {
            if (((IdleStateEvent) e).getState() == IdleState.ALL_IDLE) {
                logger.warn("Time out,connection will close");
                ctx.getChannel().close();
            }
            if (((IdleStateEvent) e).getState() == IdleState.READER_IDLE) {
                logger.warn("Reader Time out,connection will close");
                ctx.getChannel().close();
            }
            if (((IdleStateEvent) e).getState() == IdleState.WRITER_IDLE) {
                logger.warn("Writer Time out,connection will close");
                ctx.getChannel().close();
            }
        } else {
            super.handleUpstream(ctx, e);
        }
    }
}
