package com.cc.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户 Mapper
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM sys_user WHERE user_name = #{username}")
    SysUser selectByUsername(@Param("username") String username);

    /**
     * 根据邮箱查询用户
     */
    @Select("SELECT * FROM sys_user WHERE email = #{email}")
    SysUser selectByEmail(@Param("email") String email);

    /**
     * 根据手机号查询用户
     */
    @Select("SELECT * FROM sys_user WHERE phone = #{phone}")
    SysUser selectByPhone(@Param("phone") String phone);

    /**
     * 分页查询用户列表
     */
    List<SysUser> selectUserPage(@Param("userName") String userName,
                                  @Param("status") Integer status,
                                  @Param("deptId") Long deptId);

    /**
     * 根据角色 ID 查询用户列表
     */
    List<SysUser> selectUsersByRoleId(@Param("roleId") Long roleId);
}
