package com.cc.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限范围
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum {

    ALL(1, "全部数据权限"),
    CUSTOM(2, "自定义数据权限"),
    DEPT(3, "本部门数据权限"),
    DEPT_AND_CHILD(4, "本部门及以下数据权限"),
    SELF(5, "仅本人数据权限");

    private final int code;
    private final String desc;

    public static DataScopeEnum of(int code) {
        for (DataScopeEnum scope : values()) {
            if (scope.code == code) return scope;
        }
        return SELF;
    }
}
