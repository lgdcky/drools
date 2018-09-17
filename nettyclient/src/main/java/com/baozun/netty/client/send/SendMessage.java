package com.baozun.netty.client.send;

import com.baozun.netty.client.command.RuleCommand;
import com.baozun.netty.client.tools.ThreadTool;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/6/18
 * Time: 4:29 PM
 */
public class SendMessage<T> {

    private static Logger logger = LoggerFactory.getLogger(SendMessage.class);


    private static final String QUERYALLSUCCESS = "SUCCESS";

    private static final String QUERYPARTLYSUCCESS = "PARTIALSUCCESS";

    private static final String QUERYFAILED = "FAILED";

    private ThreadTool threadTool;

    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ThreadTool getThreadTool() {
        return threadTool;
    }

    public void setThreadTool(ThreadTool threadTool) {
        this.threadTool = threadTool;
    }

    public List<Map<String, Object[]>> sendMessage(RuleCommand<T> message) throws Exception {
        List<Map<String, Object[]>> dataList = message.getRuleCommandList();

        List<Future<ChannelFuture>> resultList = new LinkedList<>();

        ExecutorService executorService = threadTool.createExecutorService();
        for (Map<String, Object[]> map : dataList) {
            resultList.add(executorService.submit(new SendMessageTask(map, channel)));
        }
        return dataList;
    }
}
