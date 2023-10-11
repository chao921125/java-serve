package com.cc.net.common.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author cc
 */
@Getter
@Component
@ConfigurationProperties(prefix = "cc")
public class ProjectProperties {
    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 版权年份
     */
    private String copyrightYear;

    /**
     * 实例演示开关
     */
    private boolean demoEnabled;

    /**
     * 上传路径
     */
    @Getter
    private static String profile;

    /**
     * 获取地址开关
     */
    @Getter
    private static boolean addressEnabled;

    /**
     * 验证码类型
     */
    @Getter
    private static String captchaType;

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public void setDemoEnabled(boolean demoEnabled) {
        this.demoEnabled = demoEnabled;
    }

    public void setProfile(String profile) {
        ProjectProperties.profile = profile;
    }

    public void setAddressEnabled(boolean addressEnabled) {
        ProjectProperties.addressEnabled = addressEnabled;
    }

    public void setCaptchaType(String captchaType) {
        ProjectProperties.captchaType = captchaType;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath() {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getProfile() + "/upload";
    }
}
