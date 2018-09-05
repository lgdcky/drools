package com.server.factory;

import com.server.config.CustomKieBaseConfiguration;
import com.server.rpc.RPCBufferConfig;
import com.server.rpc.RPCServerConfig;
import com.server.utility.TestNgBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/20/18
 * Time: 11:42 AM
 */
public class configTest extends TestNgBase {

    @Autowired
    private CustomKieBaseConfiguration customKieBaseConfiguration;

    @Autowired
    private RPCBufferConfig rpcBufferConfig;

    @Autowired
    private RPCServerConfig rpcServerConfig;

    @Test
    private void loadDroolsConfig(){
        System.out.println(customKieBaseConfiguration);
    }

    @Test
    private void loadRpcBufferConfig(){
        System.out.println(rpcBufferConfig);
    }

    @Test
    private void loadRpcServerConfig(){
        System.out.println(rpcServerConfig);
    }

}
