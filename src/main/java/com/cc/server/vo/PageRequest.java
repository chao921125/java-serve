package com.cc.server.vo;

public class PageRequest {
    private int pageNum = 1;    // 当前页码，默认1
    private int pageSize = 10;  // 每页条数，默认10

    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
} 