package com.cc.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.SysUserRole;
import com.cc.core.mapper.SysUserRoleMapper;
import com.cc.core.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关联服务实现
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        List<SysUserRole> list = baseMapper.selectByUserId(userId);
        return list.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        baseMapper.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteByRoleId(Long roleId) {
        baseMapper.deleteByRoleId(roleId);
    }

    @Override
    @Transactional
    public void batchInsert(Long userId, List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) return;
        List<SysUserRole> list = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        saveBatch(list);
    }
}
