package com.cc.framework.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP 工具类
 */
public final class IpUtil {

    private IpUtil() {}

    private static final String UNKNOWN = "unknown";
    private static final String LOCAL_IP_V6 = "0:0:0:0:0:0:0:1";
    private static final String LOCAL_IP_V4 = "127.0.0.1";

    /**
     * 获取客户端真实 IP
     */
    public static String getClientIp(HttpServletRequest request) {
        if (request == null) return UNKNOWN;

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 取第一个非 unknown 的 IP
        if (ip != null && ip.contains(",")) {
            for (String s : ip.split(",")) {
                String trimmed = s.trim();
                if (!UNKNOWN.equalsIgnoreCase(trimmed)) {
                    return trimmed;
                }
            }
            ip = ip.split(",")[0].trim();
        }

        // 本地地址转换
        if (LOCAL_IP_V6.equals(ip)) {
            ip = LOCAL_IP_V4;
        }

        return ip;
    }

    /**
     * 获取服务器 IP
     */
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return LOCAL_IP_V4;
        }
    }

    /**
     * 获取服务器主机名
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return UNKNOWN;
        }
    }
}
