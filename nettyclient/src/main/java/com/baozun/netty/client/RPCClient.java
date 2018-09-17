package com.baozun.netty.client;

import com.baozun.netty.client.manager.OperationManager;
import com.baozun.netty.client.send.CustChannelFutureListener;
import org.jboss.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/30/18
 * Time: 3:03 PM
 */
public class RPCClient {

    private static Logger logger = LoggerFactory.getLogger(RPCClient.class);

    private NettyConfig nettyConfig;

    private OperationManager operationManager;

    public ChannelFuture start() {
        ChannelFuture channelFuture = nettyConfig.clientBootstrap().connect(nettyConfig.ServerAddress());
        channelFuture.addListener(new CustChannelFutureListener(operationManager));
        logger.info("netty start!");
        return channelFuture;
    }

    public NettyConfig getNettyConfig() {
        return nettyConfig;
    }

    public void setNettyConfig(NettyConfig nettyConfig) {
        this.nettyConfig = nettyConfig;
    }

}
