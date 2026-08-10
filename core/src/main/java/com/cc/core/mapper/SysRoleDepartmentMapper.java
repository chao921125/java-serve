package com.cc.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.SysRoleDepartment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色部门关联 Mapper
 */
@Mapper
public interface SysRoleDepartmentMapper extends BaseMapper<SysRoleDepartment> {

    @Select("SELECT * FROM sys_role_department WHERE role_id = #{roleId}")
    List<SysRoleDepartment> selectByRoleId(@Param("roleId") Long roleId);

    @Delete("DELETE FROM sys_role_department WHERE role_id = #{roleId}")
    int deleteByRoleId(@Param("roleId") Long roleId);

    @Select("SELECT department_id FROM sys_role_department WHERE role_id = #{roleId}")
    List<Long> selectDeptIdsByRoleId(@Param("roleId") Long roleId);
}
