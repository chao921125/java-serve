package service;

import entity.SysRole;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
    * 角色表 服务类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
    public interface SysRoleService extends IService<SysRole> {
    /**
    *  查询表sys_role所有信息
    */
    List<SysRole> selectAllSysRole();

            /**
            *  根据主键id查询表sys_role信息
            *  @param id
            */
            SysRole selectSysRoleByid(@Param("id") Long id);

    /**
    *  根据条件查询表sys_role信息
    *  @param sysRole
    */
    List<SysRole> selectSysRoleByCondition(SysRole sysRole);

            /**
            *  根据主键id查询表sys_role信息
            *  @param id
            */
            Integer deleteSysRoleByid(@Param("id") Long id);

            /**
            *  根据主键id更新表sys_role信息
            *  @param sysRole
            */
            Integer updateSysRoleByid(SysRole sysRole);

            /**
            *  新增表sys_role信息
            *  @param sysRole
            */
            Integer insertSysRole(SysRole sysRole);
    }
