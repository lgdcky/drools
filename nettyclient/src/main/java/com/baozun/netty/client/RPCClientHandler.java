package com.baozun.netty.client;

import com.baozun.netty.client.exception.ExceptionHandler;
import com.baozun.netty.client.manager.MessageHandleManager;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.baozun.netty.client.Heartbeat.HEARTBEATEND;
import static com.baozun.netty.client.Heartbeat.HEARTBEATSTART;
import static com.baozun.netty.client.tools.NettyMessageTool.convertBytes;
import static com.baozun.netty.client.tools.NettyMessageTool.convertStringAndSend;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/30/18
 * Time: 2:15 PM
 */
public class RPCClientHandler extends SimpleChannelHandler {

    private static Logger logger = LoggerFactory.getLogger(RPCClientHandler.class);

    private MessageHandleManager messageHandleManager;

    RPCClientHandler(MessageHandleManager messageHandleManager) {
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
        if(message instanceof String){
            if (HEARTBEATSTART.equals(message)) {
                convertStringAndSend(ctx, e, HEARTBEATEND);
            }else{
                return;
            }
        }else{
            messageHandleManager.messageHandle(message);
        }
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
}
