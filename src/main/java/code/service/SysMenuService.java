package service;

import entity.SysMenu;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
    * 菜单表 服务类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
    public interface SysMenuService extends IService<SysMenu> {
    /**
    *  查询表sys_menu所有信息
    */
    List<SysMenu> selectAllSysMenu();

            /**
            *  根据主键id查询表sys_menu信息
            *  @param id
            */
            SysMenu selectSysMenuByid(@Param("id") Long id);

    /**
    *  根据条件查询表sys_menu信息
    *  @param sysMenu
    */
    List<SysMenu> selectSysMenuByCondition(SysMenu sysMenu);

            /**
            *  根据主键id查询表sys_menu信息
            *  @param id
            */
            Integer deleteSysMenuByid(@Param("id") Long id);

            /**
            *  根据主键id更新表sys_menu信息
            *  @param sysMenu
            */
            Integer updateSysMenuByid(SysMenu sysMenu);

            /**
            *  新增表sys_menu信息
            *  @param sysMenu
            */
            Integer insertSysMenu(SysMenu sysMenu);
    }
