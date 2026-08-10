package com.cc.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作状态
 */
@Getter
@AllArgsConstructor
public enum BusinessStatus {

    SUCCESS(0, "成功"),
    FAIL(1, "失败");

    private final int code;
    private final String desc;
}
