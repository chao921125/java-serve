package com.cc.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.LogLogin;

/**
 * 登录日志服务接口
 */
public interface LogLoginService extends IService<LogLogin> {

    /**
     * 清空登录日志
     */
    void cleanAll();
}
