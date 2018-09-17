package com.server.rpc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/29/18
 * Time: 3:27 PM
 */

@Component
@ConfigurationProperties(prefix = "netty.server")
public class RPCServerConfig {

    private Integer port;

    private Integer idleReadTime;

    private Integer idleWriteTime;

    private Integer readTime;

    private Integer writeTime;

    private Integer idleTime;

    private Integer timeout;

    private Integer threadNum;

    public Integer getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(Integer threadNum) {
        this.threadNum = threadNum;
    }

    public Integer getReadTime() {
        return readTime;
    }

    public void setReadTime(Integer readTime) {
        this.readTime = readTime;
    }

    public Integer getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(Integer writeTime) {
        this.writeTime = writeTime;
    }

    public Integer getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(Integer idleTime) {
        this.idleTime = idleTime;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getIdleReadTime() {
        return idleReadTime;
    }

    public void setIdleReadTime(Integer idleReadTime) {
        this.idleReadTime = idleReadTime;
    }

    public Integer getIdleWriteTime() {
        return idleWriteTime;
    }

    public void setIdleWriteTime(Integer idleWriteTime) {
        this.idleWriteTime = idleWriteTime;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
