package com.cc.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色 Mapper
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户 ID 查询角色列表
     */
    @Select("""
            SELECT r.* FROM sys_role r
            LEFT JOIN sys_user_role ur ON r.id = ur.role_id
            WHERE ur.user_id = #{userId}
            """)
    List<SysRole> selectRolesByUserId(@Param("userId") Long userId);

    /**
     * 根据角色编码查询角色
     */
    @Select("SELECT * FROM sys_role WHERE code = #{code}")
    SysRole selectByCode(@Param("code") String code);

    /**
     * 查询所有正常角色
     */
    @Select("SELECT * FROM sys_role WHERE status = 0 ORDER BY sort ASC")
    List<SysRole> selectAllEnabled();
}
