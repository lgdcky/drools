package com.server.manager.handle;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/7/18
 * Time: 2:27 PM
 */
public interface MessageHandleManager {

    public byte[] messageHandle(String message) throws IOException;

    public byte[] messageHandle(Map message) throws IOException;

}