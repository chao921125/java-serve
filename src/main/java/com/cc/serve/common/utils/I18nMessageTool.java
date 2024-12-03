package com.cc.serve.common.utils;

//import java.util.Locale;
//import org.demo.security.common.web.service.DatabaseI18nMessageSource;
//import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class I18nMessageTool {

//    private static final ReloadableResourceBundleMessageSource fileSource;
//
//    private static DatabaseI18nMessageSource databaseSource;
//
//    public static final Object[] EMPTY_ARGS = new Object[0];
//
//    private static final String SEPARATOR = ":";
//
//    static {
//        fileSource = new ReloadableResourceBundleMessageSource();
//        fileSource.setBasename("classpath:i18n/messages");
//        fileSource.setDefaultEncoding("UTF-8");
//        fileSource.setCacheSeconds(3600);
//        fileSource.setAlwaysUseMessageFormat(false);
//        fileSource.setFallbackToSystemLocale(true);
//    }
//
//    public static String translate(String message, Object[] args) {
//        if (I18nMessageTool.fileSource == null && I18nMessageTool.databaseSource == null) {
//            // 不进行国际化翻译，直接提取默认值
//            return getDefaultValue(message);
//        }
//
//        String i18nKey = getI18nKey(message);
//        if (i18nKey == null) {
//            return message;
//        }
//
//        try {
//            Locale language = LanguageTool.getRequestLanguage();
//            String i18nValue = null;
//
//            // 先查数据库
//            if (I18nMessageTool.databaseSource != null) {
//                i18nValue = databaseSource.getMessage(i18nKey, args, language);
//                if (i18nValue != null) {
//                    // 如果数据库能查到翻译结果，则直接返回
//                    return i18nValue;
//                }
//            }
//
//            // 数据库查不到数据库，就到src/i18n/messages目录项目找文件。
//            if (I18nMessageTool.fileSource != null) {
//                // 这里如果找不到，会抛异常
//                return fileSource.getMessage(i18nKey, args, language);
//            }
//
//            // 数据库和国际化文件都找不到翻译，直接返回默认值
//            return getDefaultValue(i18nKey);
//        } catch (Exception e) {
//            // 找不到国际化翻译，提取默认值
//            return getDefaultValue(message);
//        }
//    }
//
//    public static String translate(String message) {
//        return translate(message, EMPTY_ARGS);
//    }
//
//    /**
//     * 提取默认国际化翻译的Key：假设message的值是“${error.msg:错误消息}”，那么提出出来的结果是：“error.msg”
//     */
//    private static String getI18nKey(String message) {
//        if (message == null || !message.startsWith("${")) {
//            // 普通字符串，没有设置i18nKey
//            return null;
//        }
//        int firstIndexOfSeparator = message.indexOf(SEPARATOR);
//        if (firstIndexOfSeparator == -1) {
//            // 没有设置value。比如：${test}，提取出来的的结果是：test
//            return message.substring(2, message.length() - 1);
//        }
//        return message.substring(2, firstIndexOfSeparator);
//    }
//
//    /**
//     * 提取默认国际化翻译：假设message的值是“${error.msg:错误消息}”，那么提出出来的结果是：“错误消息”
//     */
//    private static String getDefaultValue(String message) {
//        if (message == null || !message.startsWith("${")) {
//            // 普通字符串
//            return message;
//        }
//        int firstIndexOfSeparator = message.indexOf(SEPARATOR);
//        if (firstIndexOfSeparator == -1) {
//            // 只有key，没有设置value。比如：${test}，
//            return message;
//        }
//        return message.substring(firstIndexOfSeparator + 1, message.length() - 1);
//    }
//
//    public static void setDatabaseSource(DatabaseI18nMessageSource databaseSource) {
//        I18nMessageTool.databaseSource = databaseSource;
//    }
}
