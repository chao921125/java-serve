package com.cc.serve.common.enums;

/**
 * 操作状态
 *
 * @author cc
 */
public enum BusinessStatus {
    /**
     * 成功
     */
    SUCCESS(1, "成功"),

    /**
     * 失败
     */
    FAIL(0, "失败"),
    ;

    BusinessStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }

    private final int code;
    private final String info;

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
