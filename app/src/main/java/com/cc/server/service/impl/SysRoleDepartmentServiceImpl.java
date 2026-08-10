package com.cc.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.SysRoleDepartment;
import com.cc.core.mapper.SysRoleDepartmentMapper;
import com.cc.core.service.SysRoleDepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色部门关联服务实现（自定义数据权限）
 */
@Service
public class SysRoleDepartmentServiceImpl extends ServiceImpl<SysRoleDepartmentMapper, SysRoleDepartment> implements SysRoleDepartmentService {

    @Override
    public List<Long> getDeptIdsByRoleId(Long roleId) {
        return baseMapper.selectDeptIdsByRoleId(roleId);
    }

    @Override
    @Transactional
    public void deleteByRoleId(Long roleId) {
        baseMapper.deleteByRoleId(roleId);
    }

    @Override
    @Transactional
    public void batchInsert(Long roleId, List<Long> deptIds) {
        if (deptIds == null || deptIds.isEmpty()) return;
        List<SysRoleDepartment> list = new ArrayList<>();
        for (Long deptId : deptIds) {
            SysRoleDepartment rd = new SysRoleDepartment();
            rd.setRoleId(roleId);
            rd.setDepartmentId(deptId);
            list.add(rd);
        }
        saveBatch(list);
    }
}
