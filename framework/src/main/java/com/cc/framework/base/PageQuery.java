package com.cc.framework.base;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * 分页查询参数
 */
@Data
public class PageQuery {

    /** 当前页码 */
    @Min(value = 1, message = "页码最小为 1")
    private int pageNum = 1;

    /** 每页大小 */
    @Min(value = 1, message = "每页大小最小为 1")
    @Max(value = 1000, message = "每页大小最大为 1000")
    private int pageSize = 10;

    /** 排序列 */
    private String orderByColumn;

    /** 排序方向 asc/desc */
    private String isAsc = "desc";

    /**
     * 计算偏移量
     */
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
