package com.baozun.netty.client.manager;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/7/18
 * Time: 3:17 PM
 */
public class MessageHandleManagerImpl implements MessageHandleManager {

    @Override
    public void messageConvert(String message) {
        System.out.println(message.length() + "  handle");
    }
}
