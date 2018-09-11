package com.baozun.netty.client.manager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/7/18
 * Time: 3:03 PM
 */
public interface MessageHandleManager {

    public void messageConvert(String message);

    public void messageHandle(Object message) throws IOException;

}
