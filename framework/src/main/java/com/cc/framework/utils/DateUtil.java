package com.cc.framework.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 */
public final class DateUtil {

    private DateUtil() {}

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 格式化日期
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) return null;
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 解析日期字符串
     */
    public static LocalDateTime parse(String dateStr, String pattern) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前日期字符串
     */
    public static String now() {
        return format(LocalDateTime.now(), YYYY_MM_DD_HH_MM_SS);
    }
}
