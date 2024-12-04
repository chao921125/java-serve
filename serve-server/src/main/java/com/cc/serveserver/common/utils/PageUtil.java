package com.cc.serveserver.common.utils;

import com.cc.serve.common.core.PageEntity;
import com.cc.serve.common.core.PageSupport;
import com.github.pagehelper.PageHelper;

public class PageUtil extends PageHelper {
    public static void startPage() {
        PageEntity pageEntity = PageSupport.buildPageRequest();
        Integer pageNumber = pageEntity.getPageNumber();
        Integer pageSize = pageEntity.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageEntity.getOrderBy());
        Boolean reasonable = pageEntity.getReasonable();
        PageHelper.startPage(pageNumber, pageSize, orderBy).setReasonable(reasonable);
    }

    public static void cleanPage() {
        PageHelper.clearPage();
    }
}
