package com.server.rpc;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/30/18
 * Time: 12:28 AM
 */
public class Heartbeat extends IdleStateAwareChannelHandler {

    private static Logger logger = LoggerFactory.getLogger(Heartbeat.class);

    private int count = 0;

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
     * Invoked when an exception was raised by an I/O thread or a
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
}
