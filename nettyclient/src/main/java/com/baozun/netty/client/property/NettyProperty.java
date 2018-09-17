package com.baozun.netty.client.property;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/30/18
 * Time: 1:55 PM
 */
public class NettyProperty {

    private Integer maxFrameLength;

    private Integer lengthFieldOffset;

    private Integer lengthFieldLength;

    private Integer lengthAdjustment;

    private Integer initialBytesToStrip;

    private Integer port;

    private String ip;

    private Integer idleReadTime;

    private Integer idleWriteTime;

    private Integer readTime;

    private Integer writeTime;

    private Integer idleTime;

    private Integer timeout;

    private Integer threadMun;

    public Integer getThreadMun() {
        return threadMun;
    }

    public void setThreadMun(Integer threadMun) {
        this.threadMun = threadMun;
    }

    public Integer getMaxFrameLength() {
        return maxFrameLength;
    }

    public void setMaxFrameLength(Integer maxFrameLength) {
        this.maxFrameLength = maxFrameLength;
    }

    public Integer getLengthFieldOffset() {
        return lengthFieldOffset;
    }

    public void setLengthFieldOffset(Integer lengthFieldOffset) {
        this.lengthFieldOffset = lengthFieldOffset;
    }

    public Integer getLengthFieldLength() {
        return lengthFieldLength;
    }

    public void setLengthFieldLength(Integer lengthFieldLength) {
        this.lengthFieldLength = lengthFieldLength;
    }

    public Integer getLengthAdjustment() {
        return lengthAdjustment;
    }

    public void setLengthAdjustment(Integer lengthAdjustment) {
        this.lengthAdjustment = lengthAdjustment;
    }

    public Integer getInitialBytesToStrip() {
        return initialBytesToStrip;
    }

    public void setInitialBytesToStrip(Integer initialBytesToStrip) {
        this.initialBytesToStrip = initialBytesToStrip;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
