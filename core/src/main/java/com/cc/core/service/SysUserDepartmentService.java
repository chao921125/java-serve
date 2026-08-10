package com.cc.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.SysUserDepartment;

/**
 * 用户部门关联服务接口
 */
public interface SysUserDepartmentService extends IService<SysUserDepartment> {

    /**
     * 根据用户 ID 查询部门 ID
     */
    Long getDeptIdByUserId(Long userId);

    /**
     * 根据用户 ID 删除关联
     */
    void deleteByUserId(Long userId);

    /**
     * 新增或更新用户部门关联
     */
    void saveOrUpdateDept(Long userId, Long deptId);
}
