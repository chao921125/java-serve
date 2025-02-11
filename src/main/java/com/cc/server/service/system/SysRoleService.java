package com.cc.server.service.system;

import com.cc.server.model.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
public interface SysRoleService extends IService<SysRole> {
    List<SysRole> getRoleAll();
}
