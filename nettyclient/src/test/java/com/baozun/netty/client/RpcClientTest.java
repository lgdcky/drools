package com.baozun.netty.client;
/*
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;*/

import com.baozun.netty.client.command.RuleCommand;
import com.baozun.netty.client.manager.MessageHandleManagerImpl;
import com.baozun.netty.client.property.NettyProperty;
import com.baozun.netty.client.send.SendMessage;
import com.baozun.netty.client.tools.CompressTool;
import com.server.project.wms4.OdoCommand;
import com.server.project.wms4.WhOdoLineCommand;
import org.jboss.netty.buffer.*;
import org.jboss.netty.channel.ChannelFuture;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/30/18
 * Time: 3:06 PM
 */
public class RpcClientTest {

    //默认8K
    public static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

    public static final int MAX_BUFFER_SIZE = 16 * 1024;

    public static final int MIN_BUFFER_SIZE = 1 * 1024;

    @Test(threadPoolSize = 5, invocationCount = 10, timeOut = 10000)
    public void rpcConnectionTest() throws IOException {

        RPCClient rpcClient = initClient();

        List<OdoCommand> odoCommands = new ArrayList<OdoCommand>();
        for (int i = 0; i < 5000; i++) {
            odoCommands.add(dataBuilder(i));
        }
        Map<String, List<OdoCommand>> map = new HashMap<String, List<OdoCommand>>();
        map.put("test", odoCommands);

        String fJson = "";
        //String fJson = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
        long start = System.currentTimeMillis();
        System.out.println(fJson.length());
        System.out.println(fJson.getBytes().length + "a");
        byte[] bytes = CompressTool.compresss(fJson.getBytes());

        List<ChannelBuffer> channelBuffers = new ArrayList<ChannelBuffer>();


        int count = (int) Math.ceil(((double) bytes.length) / DEFAULT_BUFFER_SIZE);

        int size = 0;

        for (int i = 0; i < count; i++) {
            ChannelBuffer buffer_c = new BigEndianHeapChannelBuffer(DEFAULT_BUFFER_SIZE);
            size = i * DEFAULT_BUFFER_SIZE < bytes.length ? DEFAULT_BUFFER_SIZE : bytes.length - ((i - 1) * DEFAULT_BUFFER_SIZE);
            byte[] bytesdest = new byte[size];
            System.arraycopy(bytes, i * DEFAULT_BUFFER_SIZE, bytesdest, 0, size);
            buffer_c.writeBytes(bytesdest);
            channelBuffers.add(buffer_c);
        }

        System.out.println("send package size is " + channelBuffers.size());

        ChannelBuffer buffer = new CompositeChannelBuffer(ByteOrder.BIG_ENDIAN, channelBuffers);


        ChannelBuffer bufferByte = new ByteBufferBackedChannelBuffer(ByteBuffer.wrap(bytes));

        /*ChannelBuffer buffer = new DynamicChannelBuffer(bytes.length);
        dynamicChannelBuffer.writeBytes(bytes);*/
        System.out.println(bytes.length + "b");
        List<ChannelBuffer> decompose = ((CompositeChannelBuffer) buffer).decompose(buffer.readerIndex(), buffer.readableBytes());
        System.out.println("send package size is " + decompose.size());
        ChannelBuffer bufferTest = ChannelBuffers.dynamicBuffer();
        bufferTest.writeBytes(bytes);
        ChannelFuture channelFuture = rpcClient.getChannel().write(bufferByte);
        bufferTest.clear();
        System.out.println("===============================" + (System.currentTimeMillis() - start));
        System.out.println("Done");
    }

    public static RPCClient initClient() {
        NettyProperty nettyProperty = new NettyProperty();
        nettyProperty.setIdleReadTime(20);
        nettyProperty.setIdleTime(20);
        nettyProperty.setIdleWriteTime(20);
        nettyProperty.setInitialBytesToStrip(4);
        nettyProperty.setLengthAdjustment(0);
        nettyProperty.setLengthFieldLength(4);
        nettyProperty.setLengthFieldOffset(0);
        nettyProperty.setMaxFrameLength(Integer.MAX_VALUE);
        nettyProperty.setPort(8092);
        nettyProperty.setReadTime(20);
        nettyProperty.setWriteTime(20);
        nettyProperty.setIp("127.0.0.1");

        NettyConfig config = new NettyConfig();
        config.setNettyProperty(nettyProperty);
        config.setMessageHandleManager(new MessageHandleManagerImpl());
        RPCClient rpcClient = new RPCClient();
        rpcClient.setNettyConfig(config);
        rpcClient.start();
        return rpcClient;
    }

    public static OdoCommand dataBuilder(int i) {
        String t = "" + i;
        OdoCommand odoCommand = new OdoCommand();
        odoCommand.setAmt(new Double(1));
        odoCommand.setOuId(Long.valueOf(t));
        odoCommand.setOdoType(String.valueOf(new Random().nextInt(5)));
        odoCommand.setCustomerId(Long.valueOf(new Random().nextInt(2)));
        odoCommand.setOdoIndex(String.valueOf(new Random().nextInt(2)));
        odoCommand.setActualQty(Double.parseDouble(String.valueOf(new Random().nextInt(10))));
        List<WhOdoLineCommand> whOdoLineCommands = new ArrayList<WhOdoLineCommand>();
        WhOdoLineCommand whOdoLineCommand1 = new WhOdoLineCommand();
        whOdoLineCommand1.setQty(Double.parseDouble(t));
        whOdoLineCommand1.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand1.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand1);
        WhOdoLineCommand whOdoLineCommand2 = new WhOdoLineCommand();
        whOdoLineCommand2.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand2.setQty(Double.parseDouble(t));
        whOdoLineCommand2.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand2);
        WhOdoLineCommand whOdoLineCommand3 = new WhOdoLineCommand();
        whOdoLineCommand3.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand3.setQty(Double.parseDouble(t));
        whOdoLineCommand3.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand3);
        WhOdoLineCommand whOdoLineCommand4 = new WhOdoLineCommand();
        whOdoLineCommand4.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand4.setQty(Double.parseDouble(t));
        whOdoLineCommand4.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand4);
        WhOdoLineCommand whOdoLineCommand5 = new WhOdoLineCommand();
        whOdoLineCommand5.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand5.setQty(Double.parseDouble(t));
        whOdoLineCommand5.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand5);

        odoCommand.setWhOdoLineCommands(whOdoLineCommands);
        return odoCommand;
    }


    @Test(threadPoolSize = 2, invocationCount = 5, timeOut = 10000)
    public void send() throws Exception {
        RPCClient rpcClient = initClient();

        List<OdoCommand> odoCommands = new ArrayList<OdoCommand>();
        for (int i = 0; i < 10000; i++) {
            odoCommands.add(dataBuilder(i));
        }

        RuleCommand<OdoCommand> ruleCommand = new RuleCommand<OdoCommand>();
        ruleCommand.setFactList(odoCommands);
        ruleCommand.setGroup("测试一组");
        ruleCommand.setType("odoCommandFact");

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChannel(rpcClient.getChannel());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
        System.out.println(formatter.format(new Date()) + "   start");
        System.out.println(sendMessage.sendMessage(ruleCommand));

    }

}
