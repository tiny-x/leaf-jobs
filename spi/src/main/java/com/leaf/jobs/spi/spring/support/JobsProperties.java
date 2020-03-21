package com.leaf.jobs.spi.spring.support;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "leaf.jobs")
public class JobsProperties {

    /**
     * 注册中心地址
     */
    private String registerAddress;

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 服务端口
     */
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

}
