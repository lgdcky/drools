package com.server.rpc.heartbeat;

import org.jboss.netty.buffer.ByteBufferBackedChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

import static com.server.tools.CompressTool.compresss;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/30/18
 * Time: 12:28 AM
 */
public class Heartbeat extends IdleStateAwareChannelHandler {

    private static Logger logger = LoggerFactory.getLogger(Heartbeat.class);

    private int count = 0;

    public static final String HEARTBEATSTART = "start";

    public static final String HEARTBEATEND = "end";

    /**
     * Invoked when a {@link Channel} has been idle for a while.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
        super.channelIdle(ctx, e);
        if (e.getState() == IdleState.ALL_IDLE)
            count++;
        if (count == 3) {
            e.getChannel().close();
            logger.info("连接断开!");
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
        if (this == ctx.getPipeline().getLast()) {
            logger.warn(
                    "EXCEPTION, please implement " + getClass().getName() +
                            ".exceptionCaught() for proper handling.", e.getCause());
        }
        ctx.sendUpstream(e);
        super.exceptionCaught(ctx, e);
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof IdleStateEvent) {
            if (((IdleStateEvent) e).getState() == IdleState.ALL_IDLE) {
                logger.warn("will check client state!");
                ChannelBuffer bufferByte = new ByteBufferBackedChannelBuffer(ByteBuffer.wrap(compresss(HEARTBEATSTART.getBytes())));
                ctx.getChannel().write(bufferByte);
                bufferByte.clear();
            }
            if (((IdleStateEvent) e).getState() == IdleState.READER_IDLE) {
                logger.warn("Reader Time out,load data failed!");
            }
            if (((IdleStateEvent) e).getState() == IdleState.WRITER_IDLE) {
                logger.warn("Writer Time out,load data failed!");
            }
        } else {
            super.handleUpstream(ctx, e);
        }
    }

}
