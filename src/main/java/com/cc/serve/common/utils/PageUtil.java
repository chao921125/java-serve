package com.cc.serve.common.utils;

import com.cc.serve.common.PageEntity;
import com.github.pagehelper.PageHelper;

public class PageUtil extends PageHelper {
    public static void startPage() {
        PageEntity pageEntity = new PageEntity();
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
