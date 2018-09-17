package com.baozun.netty.client.manager;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/7/18
 * Time: 3:03 PM
 */
public interface MessageHandleManager {

    public void messageHandle(Object message) throws IOException;

}
