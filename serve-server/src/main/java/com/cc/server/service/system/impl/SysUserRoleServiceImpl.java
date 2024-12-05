package com.cc.server.service.system.impl;

import com.cc.server.model.entity.system.SysUserRole;
import com.cc.server.mapper.system.SysUserRoleMapper;
import com.cc.server.service.system.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色 用户N-1角色 服务实现类
 * </p>
 *
 * @author cc
 * @since 2024-11-22 13:40:55
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
