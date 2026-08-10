package com.cc.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态
 */
@Getter
@AllArgsConstructor
public enum UserStatus {

    NORMAL(0, "正常"),
    DISABLED(1, "停用"),
    DELETED(2, "删除");

    private final int code;
    private final String desc;

    public static UserStatus of(int code) {
        for (UserStatus status : values()) {
            if (status.code == code) return status;
        }
        return NORMAL;
    }
}
