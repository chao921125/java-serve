package com.cc.serveserver.common.core;

import com.cc.serveserver.common.utils.ConvertUtil;
import com.cc.serveserver.common.utils.ServletUtil;

public class PageSupport {
    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNumber";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 分页参数合理化
     */
    public static final String REASONABLE = "reasonable";

    /**
     * 封装分页对象
     */
    public static PageEntity getPageEntity() {
        PageEntity pageEntity = new PageEntity();
        pageEntity.setPageNumber(ConvertUtil.toInt(ServletUtil.getParameter(PAGE_NUM), 1));
        pageEntity.setPageSize(ConvertUtil.toInt(ServletUtil.getParameter(PAGE_SIZE), 10));
        pageEntity.setOrderByColumn(ServletUtil.getParameter(ORDER_BY_COLUMN));
        pageEntity.setIsAsc(ServletUtil.getParameter(IS_ASC));
        pageEntity.setReasonable(ServletUtil.getParameterToBool(REASONABLE));
        return pageEntity;
    }

    public static PageEntity buildPageRequest() {
        return getPageEntity();
    }
}
