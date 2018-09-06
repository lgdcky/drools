package com.server.rpc;


import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.handler.timeout.WriteTimeoutHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/28/18
 * Time: 1:46 PM
 */
@Configuration
public class NettyConfig {

    @Autowired
    private RPCBufferConfig rpcBufferConfig;

    @Autowired
    private RPCServerConfig rpcServerConfig;

    @Bean(name = "serverBootstrap")
    public ServerBootstrap bootstrap() throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.setFactory(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()));
        serverBootstrap.setPipelineFactory(() -> {
            ChannelPipeline channelPipeline = Channels.pipeline();
            channelPipeline.addLast("timeout", new IdleStateHandler(new HashedWheelTimer(), rpcServerConfig.getIdleReadTime(), rpcServerConfig.getWriteTime(), rpcServerConfig.getIdleTime()));
            channelPipeline.addLast("hearbeat", new Heartbeat());
            channelPipeline.addLast("readTimeout", new ReadTimeoutHandler(new HashedWheelTimer(), rpcServerConfig.getReadTime()));
            channelPipeline.addLast("writeTimeout", new WriteTimeoutHandler(new HashedWheelTimer(), rpcServerConfig.getWriteTime()));
            channelPipeline.addLast("decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, rpcBufferConfig.getLengthFieldOffset(), rpcBufferConfig.getLengthFieldLength(), rpcBufferConfig.getLengthAdjustment(), rpcBufferConfig.getInitialBytesToStrip()));
            channelPipeline.addLast("encoder", new LengthFieldPrepender(4, false));
            channelPipeline.addLast("rpcServerHandler", new RPCServerHandler());
            return channelPipeline;
        });

        serverBootstrap.setOption("child.keepAlive", false);
        serverBootstrap.setOption("child.tcpNoDelay", true);

        return serverBootstrap;
    }

    @Bean(name = "tcpSocketAddress")
    public InetSocketAddress tcpPort() {
        return new InetSocketAddress(rpcServerConfig.getPort());
    }

}
