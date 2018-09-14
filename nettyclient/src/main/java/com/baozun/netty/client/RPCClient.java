package com.baozun.netty.client;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/30/18
 * Time: 3:03 PM
 */
public class RPCClient {

    private static Logger logger = LoggerFactory.getLogger(RPCClient.class);

    private NettyConfig nettyConfig;

    public ChannelFuture start() {
        ChannelFuture channelFuture = nettyConfig.clientBootstrap().connect(nettyConfig.ServerAddress());
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
