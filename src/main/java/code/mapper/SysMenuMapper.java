package mapper;

import entity.SysMenu;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* <p>
    * 菜单表 Mapper 接口
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
    public interface SysMenuMapper extends BaseMapper<SysMenu> {
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
