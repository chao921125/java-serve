package com.cc.net.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author cc
 */
@Component
@ConfigurationProperties(prefix = "xss")
public class XssProperties {
    /**
     * 过滤开关
     */
    private boolean enabled;

    /**
     * 排除链接（多个用逗号分隔）
     */
    private String excludes;

    /**
     * 匹配链接
     */
    private String urlPatterns;

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setExcludes(String excludes) {
        this.excludes = excludes;
    }

    public void setUrlPatterns(String urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getExcludes() {
        return excludes;
    }

    public String getUrlPatterns() {
        return urlPatterns;
    }
}
