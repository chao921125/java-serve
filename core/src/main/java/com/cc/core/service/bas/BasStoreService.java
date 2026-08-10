package com.cc.core.service.bas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.bas.BasStore;

/**
 * BasStore 服务接口
 */
public interface BasStoreService extends IService<BasStore> {


    /**
     * 切换启用状态
     */
    void toggleEnabled(Long id);

}
