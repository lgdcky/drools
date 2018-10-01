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

    private static final String ERROR = "error message";

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
        Object message = null;
        try {
            message = convertBytes(ctx, e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (message instanceof String) {
            String rec = (String) message;
            if (rec.contains(HEARTBEATSTART)) {
                if (Integer.parseInt(rec.split("_")[1]) > 10) {
                    logger.info("childChannel will be closed!");
                    ctx.getPipeline().getChannel().disconnect();
                } else {
                    convertStringAndSend(ctx, e, HEARTBEATEND);
                }
            } else {
                convertStringAndSend(ctx, e, ERROR);
            }
        } else {
            messageHandleManager.messageHandle(message);
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
        logger.info("info write complete!");
        super.writeComplete(ctx, e);
    }

    /**
     * Invoked when a child {@link Channel} was closed.
     * (e.g. the accepted connection was closed)
     *
     * @param ctx
     * @param e
     */
    @Override
    public void childChannelClosed(ChannelHandlerContext ctx, ChildChannelStateEvent e) throws Exception {
        logger.info("childChannel is closed!");
        super.childChannelClosed(ctx, e);
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
