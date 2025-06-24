package service;

import entity.SysUserRole;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
    * 用户角色 用户N-1角色 服务类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
    public interface SysUserRoleService extends IService<SysUserRole> {
    /**
    *  查询表sys_user_role所有信息
    */
    List<SysUserRole> selectAllSysUserRole();

            /**
            *  根据主键id查询表sys_user_role信息
            *  @param id
            */
            SysUserRole selectSysUserRoleByid(@Param("id") Long id);

    /**
    *  根据条件查询表sys_user_role信息
    *  @param sysUserRole
    */
    List<SysUserRole> selectSysUserRoleByCondition(SysUserRole sysUserRole);

            /**
            *  根据主键id查询表sys_user_role信息
            *  @param id
            */
            Integer deleteSysUserRoleByid(@Param("id") Long id);

            /**
            *  根据主键id更新表sys_user_role信息
            *  @param sysUserRole
            */
            Integer updateSysUserRoleByid(SysUserRole sysUserRole);

            /**
            *  新增表sys_user_role信息
            *  @param sysUserRole
            */
            Integer insertSysUserRole(SysUserRole sysUserRole);
    }
