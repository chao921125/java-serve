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
    static
    class Password {
        private Number maxRetryCount;
        private Number lockTime;

        public void setMaxRetryCount(Number maxRetryCount) {
            this.maxRetryCount = maxRetryCount;
        }

        public Number getMaxRetryCount() {
            return maxRetryCount;
        }

        public Number getLockTime() {
            return lockTime;
        }

        public void setLockTime(Number lockTime) {
            this.lockTime = lockTime;
        }
    }
    /**
     * 密码错误{maxRetryCount}次锁定10分钟
     */
    private Map<String, Number> Password;

    public void setPassword(Map<String, Number> password) {
        Password = password;
    }

    public Map<String, Number> getPassword() {
        return Password;
    }
}
