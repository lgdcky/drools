package com.baozun.netty.client;

import com.baozun.netty.client.manager.MessageHandleManager;
import com.baozun.netty.client.property.NettyProperty;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/30/18
 * Time: 1:21 PM
 */

public class NettyConfig {

    private NettyProperty nettyProperty;

    public NettyProperty getNettyProperty() {
        return nettyProperty;
    }

    private MessageHandleManager messageHandleManager;

    public MessageHandleManager getMessageHandleManager() {
        return messageHandleManager;
    }

    public void setMessageHandleManager(MessageHandleManager messageHandleManager) {
        this.messageHandleManager = messageHandleManager;
    }

    public void setNettyProperty(NettyProperty nettyProperty) {
        this.nettyProperty = nettyProperty;
    }

    public ClientBootstrap clientBootstrap() {
        ClientBootstrap clientBootstrap = new ClientBootstrap();
        clientBootstrap.setFactory(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()));
        clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline channelPipeline = Channels.pipeline();
                // 进行包装
                channelPipeline.addLast("timeout", new IdleStateHandler(new HashedWheelTimer(), nettyProperty.getIdleReadTime(), nettyProperty.getWriteTime(), nettyProperty.getIdleTime()));
                channelPipeline.addLast("hearbeat", new Heartbeat());
                channelPipeline.addLast("decoder", new LengthFieldBasedFrameDecoder(nettyProperty.getMaxFrameLength(), nettyProperty.getLengthFieldOffset(), nettyProperty.getLengthFieldLength(), nettyProperty.getLengthAdjustment(), nettyProperty.getInitialBytesToStrip()));
                channelPipeline.addLast("encoder", new LengthFieldPrepender(4, false));
                channelPipeline.addLast("rpcServerHandler", new RPCClientHandler(messageHandleManager));
                return channelPipeline;
            }
        });
        clientBootstrap.setOption("connectTimeoutMillis", nettyProperty.getTimeout());
        return clientBootstrap;
    }

    public InetSocketAddress ServerAddress() {
        return new InetSocketAddress(nettyProperty.getIp(), nettyProperty.getPort());
    }

}
