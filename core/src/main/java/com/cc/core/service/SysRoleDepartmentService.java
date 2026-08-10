package com.cc.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.SysRoleDepartment;

import java.util.List;

/**
 * 角色部门关联服务接口（自定义数据权限）
 */
public interface SysRoleDepartmentService extends IService<SysRoleDepartment> {

    /**
     * 根据角色 ID 查询部门 ID 列表
     */
    List<Long> getDeptIdsByRoleId(Long roleId);

    /**
     * 根据角色 ID 删除所有关联
     */
    void deleteByRoleId(Long roleId);

    /**
     * 批量新增角色部门关联
     */
    void batchInsert(Long roleId, List<Long> deptIds);
}
