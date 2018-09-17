package com.baozun.netty.client.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/14/18
 * Time: 4:43 PM
 */
public class ThreadTool {

    private static Logger logger = LoggerFactory.getLogger(ThreadTool.class);

    private ExecutorService executorService;

    public final ExecutorService createExecutorService() {
        executorService = Executors.newCachedThreadPool();
        return executorService;
    }

}
