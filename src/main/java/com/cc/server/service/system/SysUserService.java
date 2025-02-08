package com.cc.server.service.system;

import com.cc.server.model.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
public interface SysUserService extends IService<SysUser> {
    SysUser getUserByNameEmailPhone(String username);

    SysUser loginUser(String username, String password);
}
