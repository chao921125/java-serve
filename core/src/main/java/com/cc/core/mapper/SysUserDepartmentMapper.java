package com.cc.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.SysUserDepartment;
import com.cc.core.entity.SysDictionary;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户部门关联 Mapper
 */
@Mapper
public interface SysUserDepartmentMapper extends BaseMapper<SysUserDepartment> {

    @Select("SELECT * FROM sys_user_department WHERE user_id = #{userId} LIMIT 1")
    SysUserDepartment selectByUserId(@Param("userId") Long userId);

    @Delete("DELETE FROM sys_user_department WHERE user_id = #{userId}")
    int deleteByUserId(@Param("userId") Long userId);
}
