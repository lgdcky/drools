package com.server.rpc;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/29/18
 * Time: 11:31 PM
 */

@Component
public class RPCServer {

    private static Logger logger = LoggerFactory.getLogger(RPCServer.class);

    @Autowired
    @Qualifier("serverBootstrap")
    private ServerBootstrap serverBootstrap;

    @Autowired
    @Qualifier("tcpSocketAddress")
    private InetSocketAddress inetSocketAddress;

    private Channel channel;

    @PostConstruct
    public void start() {
        channel = serverBootstrap.bind(inetSocketAddress);
        logger.info("netty start!");
    }

    @PreDestroy
    public void stop() throws Exception {
        channel.close();
        logger.info("netty close!");
    }

}
