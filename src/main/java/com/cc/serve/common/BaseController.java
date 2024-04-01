package com.cc.serve.common;

import com.cc.serve.utils.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageUtil.startPage();
    }
}
