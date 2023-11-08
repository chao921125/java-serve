package com.cc.net.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 读取项目相关配置
 *
 * @author cc
 */
@Component
@ConfigurationProperties(prefix = "user")
public class UserProperties {
    private Number pwdMaxRetryCount;
    private Number pwdLockTime;
    private String jwtKey;

    public Number getPwdMaxRetryCount() {
        return pwdMaxRetryCount;
    }

    public void setPwdMaxRetryCount(Number pwdMaxRetryCount) {
        this.pwdMaxRetryCount = pwdMaxRetryCount;
    }

    public Number getPwdLockTime() {
        return pwdLockTime;
    }

    public void setPwdLockTime(Number pwdLockTime) {
        this.pwdLockTime = pwdLockTime;
    }

    public String getJwtKey() {
        return jwtKey;
    }

    public void setJwtKey(String jwtKey) {
        this.jwtKey = jwtKey;
    }
}
