package com.server.Rpc;

import com.server.rpc.RPCServer;
import com.server.utility.TestNgBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/29/18
 * Time: 5:51 PM
 */
public class RpcTest extends TestNgBase {

    @Autowired
    private RPCServer rpcServer;

    @Test
    public void startServer(){
        rpcServer.start();
    }

}
