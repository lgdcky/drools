package com.server.config;

import com.server.aspect.KnowLedgeBaseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/26/18
 * Time: 7:12 PM
 */

/**
 * 注册拦截
 */
@EnableAspectJAutoProxy
@Configuration
public class KnowLedgeBaseHandlerProxyConfig {

    /**
     * 用于添加知识库
     * @return
     */
    @Bean
    public KnowLedgeBaseHandler knowLedgeBaseHandler(){
        return new KnowLedgeBaseHandler();
    }

}
