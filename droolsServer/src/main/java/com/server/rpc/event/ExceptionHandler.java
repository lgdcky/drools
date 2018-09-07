package com.server.rpc.event;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/30/18
 * Time: 3:48 PM
 */
public class ExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    public static final void exceptionHandle(ChannelHandlerContext ctx, ExceptionEvent e){
        if(e.getCause() instanceof ReadTimeoutException){
            logger.warn("channel will be close!");
            ctx.getChannel().close();
        }else{
            logger.warn("unknow failed!");
        }
    }

}
