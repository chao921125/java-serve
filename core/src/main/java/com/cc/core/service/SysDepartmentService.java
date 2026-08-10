package com.cc.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.SysDepartment;

import java.util.List;

/**
 * 部门服务接口
 */
public interface SysDepartmentService extends IService<SysDepartment> {

    /**
     * 查询部门树
     */
    List<SysDepartment> getDeptTree();

    /**
     * 根据 ID 查询所有子部门 ID
     */
    List<Long> getChildDeptIds(Long deptId);
}
