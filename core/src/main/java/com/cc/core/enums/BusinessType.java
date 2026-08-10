package com.cc.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务操作类型
 */
@Getter
@AllArgsConstructor
public enum BusinessType {

    OTHER(0, "其他"),
    INSERT(1, "新增"),
    UPDATE(2, "修改"),
    DELETE(3, "删除"),
    GRANT(4, "授权"),
    EXPORT(5, "导出"),
    IMPORT(6, "导入"),
    FORCE(7, "强退"),
    CLEAN(8, "清空");

    private final int code;
    private final String desc;
}
