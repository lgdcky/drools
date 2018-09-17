package com.server.rpc;


import com.server.manager.handle.MessageHandleManager;
import com.server.rpc.exception.ExceptionHandler;
import com.server.tools.TypeConvertTools;
import org.jboss.netty.buffer.ByteBufferBackedChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Map;

import static com.server.rpc.heartbeat.Heartbeat.HEARTBEATEND;
import static com.server.rpc.heartbeat.Heartbeat.HEARTBEATSTART;
import static com.server.tools.CompressTool.compresss;
import static com.server.tools.NettyMessageTool.convertBytes;
import static com.server.tools.NettyMessageTool.convertStringAndSend;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/28/18
 * Time: 1:53 PM
 */

public class RPCServerHandler extends SimpleChannelHandler {

    private static Logger logger = LoggerFactory.getLogger(RPCServerHandler.class);

    private MessageHandleManager messageHandleManager;

    private static final String RECEIVED = "received";

    private static final Integer BUFFERSIZE = 3 * 1024 * 1024;

    RPCServerHandler(MessageHandleManager messageHandleManager) {
        this.messageHandleManager = messageHandleManager;
    }

    /**
     * Invoked when a message object (e.g: {@link ChannelBuffer}) was received
     * from a remote peer.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Object message = convertBytes(ctx, e);
        if (message instanceof String) {
            if (HEARTBEATSTART.equals(message)) {
                convertStringAndSend(ctx, e, HEARTBEATEND);
            } else {
                return;
            }
        } else {
            byte[] bytes = messageHandleManager.messageHandle((Map) message);
            ChannelBuffer dynamicDuffer = ChannelBuffers.dynamicBuffer(bytes.length);
            dynamicDuffer.writeBytes(bytes);
            ctx.getChannel().write(dynamicDuffer);
            dynamicDuffer.clear();
        }
    }

    /**
     * Invoked when something was written into a {@link Channel}.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e) throws Exception {
        logger.info("write complete!");
        super.writeComplete(ctx, e);
    }

    /**
     * Invoked when an exception was raised by an I/O heartbeat or a
     * {@link ChannelHandler}.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        logger.error(e.toString());
        ExceptionHandler.exceptionHandle(ctx, e);
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
