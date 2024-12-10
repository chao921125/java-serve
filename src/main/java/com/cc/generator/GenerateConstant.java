package com.cc.generator;

public class GenerateConstant {
    /**
     * 数据库字符串类型
     */
    public static final String[] COLUMN_TYPE_STR =  {"char", "varchar", "nvarchar", "varchar2", "tinytext", "text", "mediumtext", "longtext"};
    /**
     * 数据库时间类型
     */
    public static final String[] COLUMN_TYPE_TIME = {"datetime", "time", "date", "timestamp"};
    /**
     * 数据库数字类型
     */
    public static final String[] COLUMN_TYPE_NUMBER = {"tinyint", "smallint", "mediumint", "int", "number", "integer", "bit"};
    /**
     * 数据库bigint类型
     */
    public static final String[] COLUMN_TYPE_BIGINT = {"bigint"};
    /**
     * 数据库float类型
     */
    public static final String[] COLUMN_TYPE_FLOAT = {"float"};
    /**
     * 数据库double类型
     */
    public static final String[] COLUMN_TYPE_DOUBLE = {"double"};
    /**
     * 数据库decimal类型
     */
    public static final String[] COLUMN_TYPE_DECIMAL = {"decimal"};

    /**
     * 字符串类型
     */
    public static final String TYPE_STRING = "String";

    /**
     * 整型
     */
    public static final String TYPE_INTEGER = "Integer";

    /**
     * 长整型
     */
    public static final String TYPE_LONG = "Long";

    /**
     * 浮点型
     */
    public static final String TYPE_DOUBLE = "Double";

    /**
     * 高精度计算类型
     */
    public static final String TYPE_BIGDECIMAL = "BigDecimal";

    /**
     * 时间类型
     */
    public static final String TYPE_DATE = "Date";
}
