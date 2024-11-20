package com.cc.serve.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.serve.entity.system.SysUser;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author cc
 * @since 2024-09-28 15:03:228
 */
public interface SysUserService extends IService<SysUser> {
    public List<SysUser> listPage(SysUser user);
}
