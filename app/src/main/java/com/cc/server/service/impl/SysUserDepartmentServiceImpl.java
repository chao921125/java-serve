package com.cc.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.SysUserDepartment;
import com.cc.core.mapper.SysUserDepartmentMapper;
import com.cc.core.service.SysUserDepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户部门关联服务实现
 */
@Service
public class SysUserDepartmentServiceImpl extends ServiceImpl<SysUserDepartmentMapper, SysUserDepartment> implements SysUserDepartmentService {

    @Override
    public Long getDeptIdByUserId(Long userId) {
        SysUserDepartment ud = baseMapper.selectByUserId(userId);
        return ud != null ? ud.getDepartmentId() : null;
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        baseMapper.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public void saveOrUpdateDept(Long userId, Long deptId) {
        if (deptId == null) return;
        baseMapper.deleteByUserId(userId);
        SysUserDepartment ud = new SysUserDepartment();
        ud.setUserId(userId);
        ud.setDepartmentId(deptId);
        save(ud);
    }
}
