package mapper;

import entity.SysRoleMenu;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* <p>
    * 角色菜单 角色1-N菜单 Mapper 接口
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
    public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    /**
    *  查询表sys_role_menu所有信息
    */
    List<SysRoleMenu> selectAllSysRoleMenu();

            /**
            *  根据主键id查询表sys_role_menu信息
            *  @param id
            */
            SysRoleMenu selectSysRoleMenuByid(@Param("id") Long id);

    /**
    *  根据条件查询表sys_role_menu信息
    *  @param sysRoleMenu
    */
    List<SysRoleMenu> selectSysRoleMenuByCondition(SysRoleMenu sysRoleMenu);

            /**
            *  根据主键id查询表sys_role_menu信息
            *  @param id
            */
            Integer deleteSysRoleMenuByid(@Param("id") Long id);

            /**
            *  根据主键id更新表sys_role_menu信息
            *  @param sysRoleMenu
            */
            Integer updateSysRoleMenuByid(SysRoleMenu sysRoleMenu);

            /**
            *  新增表sys_role_menu信息
            *  @param sysRoleMenu
            */
            Integer insertSysRoleMenu(SysRoleMenu sysRoleMenu);

    }
