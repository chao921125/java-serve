package com.cc.framework.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * 字符串工具类
 */
public final class StringUtil {

    private StringUtil() {}

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.isEmpty();
    }

    /**
     * 判断字符串是否不为空
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 判断字符串是否为空白
     */
    public static boolean isBlank(CharSequence cs) {
        if (cs == null) return true;
        for (int i = 0; i < cs.length(); i++) {
            if (!Character.isWhitespace(cs.charAt(i))) return false;
        }
        return true;
    }

    /**
     * MD5 加密
     */
    public static String md5(String input) {
        return DigestUtils.md5DigestAsHex(input.getBytes(StandardCharsets.UTF_8));
    }
}
