package com.cc.framework.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 分页结果
 *
 * @param <T> 数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    /** 总记录数 */
    private long total;

    /** 列表数据 */
    private List<T> list;

    /** 当前页码 */
    private int pageNum;

    /** 每页大小 */
    private int pageSize;

    /**
     * 空结果
     */
    public static <T> PageResult<T> empty() {
        return new PageResult<>(0, Collections.emptyList(), 1, 10);
    }

    /**
     * 构建分页结果
     */
    public static <T> PageResult<T> of(long total, List<T> list, int pageNum, int pageSize) {
        return new PageResult<>(total, list, pageNum, pageSize);
    }

    /**
     * 总页数
     */
    public long getTotalPages() {
        return pageSize == 0 ? 0 : (total + pageSize - 1) / pageSize;
    }
}
