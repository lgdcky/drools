package com.baozun.netty.client;

import com.baozun.netty.client.tools.CompressTool;
import org.jboss.netty.buffer.ByteBufferBackedChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/30/18
 * Time: 2:15 PM
 */
public class RPCClientHandler extends SimpleChannelHandler {

    private static Logger logger = LoggerFactory.getLogger(RPCClientHandler.class);

    /**
     * Invoked when a message object (e.g: {@link ChannelBuffer}) was received
     * from a remote peer.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
        if (buffer.array().length <= 0) {
            return;
        }
        System.out.println(buffer.array().length);
        String JsonString = new String(CompressTool.uncompresss(buffer.array()));
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
        System.out.println(formatter.format(new Date()) + "   end");
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
}
