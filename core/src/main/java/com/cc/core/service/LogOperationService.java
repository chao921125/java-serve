package com.cc.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.LogOperation;

/**
 * 操作日志服务接口
 */
public interface LogOperationService extends IService<LogOperation> {

    /**
     * 清空操作日志
     */
    void cleanAll();
}
