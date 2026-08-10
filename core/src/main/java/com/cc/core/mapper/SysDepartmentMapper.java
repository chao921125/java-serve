package com.cc.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.SysDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 部门 Mapper
 */
@Mapper
public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {

    /**
     * 查询所有部门（按排序）
     */
    @Select("SELECT * FROM sys_department ORDER BY sort ASC")
    List<SysDepartment> selectAllOrdered();

    /**
     * 根据用户 ID 查询部门
     */
    @Select("""
            SELECT d.* FROM sys_department d
            LEFT JOIN sys_user_department ud ON d.id = ud.department_id
            WHERE ud.user_id = #{userId}
            """)
    SysDepartment selectByUserId(@Param("userId") Long userId);

    /**
     * 查询子部门列表
     */
    @Select("SELECT * FROM sys_department WHERE parent_id = #{parentId} ORDER BY sort ASC")
    List<SysDepartment> selectByParentId(@Param("parentId") Long parentId);

    /**
     * 根据名称查询部门
     */
    @Select("SELECT * FROM sys_department WHERE name = #{name} LIMIT 1")
    SysDepartment selectByName(@Param("name") String name);
}
