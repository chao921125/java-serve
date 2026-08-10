package com.cc.core.service.portal;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.portal.PortalUser;

/**
 * PortalUser 服务接口
 */
public interface PortalUserService extends IService<PortalUser> {


    /**
     * 门户登录
     */
    PortalUser login(String username, String password);

    /**
     * 检查门户用户是否启用
     */
    boolean isActive(Long id);

}
