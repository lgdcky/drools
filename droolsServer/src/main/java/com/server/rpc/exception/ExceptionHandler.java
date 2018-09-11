package com.server.rpc.exception;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ConnectException;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/30/18
 * Time: 3:48 PM
 */
public class ExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    public static final void exceptionHandle(ChannelHandlerContext ctx, ExceptionEvent e) {
        if (e.getCause() instanceof ConnectException) {
            logger.warn("channel will be closed!");
            ctx.getChannel().close();
        } else if (e.getCause() instanceof IOException) {
            logger.warn("IO exception connection will be closed!");
            ctx.getChannel().close();
        } else {
            logger.warn("unknow failed!");
        }
    }

}
