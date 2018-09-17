package com.baozun.netty.client.send;

import com.baozun.netty.client.manager.OperationManager;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 11:39 AM
 */
public class CustChannelFutureListener implements ChannelFutureListener {

    private static Logger logger = LoggerFactory.getLogger(CustChannelFutureListener.class);

    private OperationManager operationManager;

    public CustChannelFutureListener(OperationManager operationManager){
        this.operationManager = operationManager;
    }

    /**
     * Invoked when the I/O operation associated with the {@link ChannelFuture}
     * has been completed.
     *
     * @param future the source {@link ChannelFuture} which called this
     *               callback
     */
    /**
     * 用于获取发送完成的后续处理,此处为埋点
     * @param future
     * @throws Exception
     */
    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        logger.info(future.isSuccess()+"  listener");
    }
}
