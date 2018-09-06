package com.baozun.netty.client;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/30/18
 * Time: 3:03 PM
 */
public class RPCClient {

    //private static Logger logger = LoggerFactory.getLogger(RPCClient.class);

    private NettyConfig nettyConfig;

    private ChannelFuture channelFuture;

    @PostConstruct
    public void start() {
        channelFuture = nettyConfig.clientBootstrap().connect(nettyConfig.tcpPort());
        //logger.info("netty start!");
        System.out.println("connect server!");
    }

    public NettyConfig getNettyConfig() {
        return nettyConfig;
    }

    public void setNettyConfig(NettyConfig nettyConfig) {
        this.nettyConfig = nettyConfig;
    }

    public Channel getChannel() {
        return channelFuture.getChannel();
    }

}
