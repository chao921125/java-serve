package com.cc.net.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author cc
 */
@Component
@ConfigurationProperties(prefix = "sso")
public class SsoProperties {
    /**
     * 是否开启单点登陆
     */
    private boolean openFlag;

    /**
     * Server
     */
    private String ssoClientType;

    public void setOpenFlag(boolean openFlag) {
        this.openFlag = openFlag;
    }

    public void setSsoClientType(String ssoClientType) {
        this.ssoClientType = ssoClientType;
    }

    public boolean isOpenFlag() {
        return openFlag;
    }

    public String getSsoClientType() {
        return ssoClientType;
    }
}
