package com.server.rpc;


import com.server.manager.handle.MessageHandleManager;
import com.server.rpc.exception.ExceptionHandler;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

import static com.server.rpc.heartbeat.Heartbeat.HEARTBEATEND;
import static com.server.rpc.heartbeat.Heartbeat.HEARTBEATSTART;
import static com.server.tools.NettyMessageTool.convertStringAndSend;
import static com.server.tools.NettyMessageTool.convertToString;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/28/18
 * Time: 1:53 PM
 */

public class RPCServerHandler extends SimpleChannelHandler {

    private static Logger logger = LoggerFactory.getLogger(RPCServerHandler.class);

    private MessageHandleManager messageHandleManager;

    RPCServerHandler(MessageHandleManager messageHandleManager){
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
        String message = convertToString(ctx, e);
        if (HEARTBEATSTART.equals(message)) {
            convertStringAndSend(ctx, e, HEARTBEATEND);
            System.out.println("ping");
        }else if (HEARTBEATEND.equals(message)) {
            return;
        } else if (null != message) {
            System.out.println(Instant.now());
            messageHandleManager.messageHandle(message);
        }
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

}
