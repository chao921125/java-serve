package com.cc.server.service.system.impl;

import com.cc.server.model.system.entity.SysRole;
import com.cc.server.mapper.system.SysRoleMapper;
import com.cc.server.service.system.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> getRoleAll() {
        return List.of();
    }
}
