package com.cc.core.dto.bas;

import lombok.Data;

/**
 * 分类新增/修改
 */
@Data
public class CategorySaveDTO {

    /** 父级 ID */
    private Long parentId;

    /** 分类名称 */
    private String name;

    /** 分类编码 */
    private String code;

    /** 排序 */
    private Integer sort;

    /** 备注 */
    private String remark;
}
