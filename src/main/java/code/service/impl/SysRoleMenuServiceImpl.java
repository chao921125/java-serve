package service.impl;

import entity.SysRoleMenu;
import java.util.List;
import mapper.SysRoleMenuMapper;
    import service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * 角色菜单 角色1-N菜单 服务实现类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
@Service
    public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
    *  查询表sys_role_menu所有信息
    */
    @Override
    public List<SysRoleMenu> selectAllSysRoleMenu() { return sysRoleMenuMapper.selectAllSysRoleMenu();}

            /**
            *  根据主键id查询表sys_role_menu信息
            *  @param id
            */
            @Override
            public SysRoleMenu selectSysRoleMenuByid(@Param("id") Long id) { return sysRoleMenuMapper.selectSysRoleMenuByid(id);}

    /**
    *  根据条件查询表sys_role_menu信息
    *  @param sysRoleMenu
    */
    @Override
    public List<SysRoleMenu> selectSysRoleMenuByCondition(SysRoleMenu sysRoleMenu) { return sysRoleMenuMapper.selectSysRoleMenuByCondition(sysRoleMenu);}

            /**
            *  根据主键id查询表sys_role_menu信息
            *  @param id
            */
            @Override
            public Integer deleteSysRoleMenuByid(@Param("id") Long id) { return sysRoleMenuMapper.deleteSysRoleMenuByid(id);}

            /**
            *  根据主键id更新表sys_role_menu信息
            *  @param sysRoleMenu
            */
            @Override
            public Integer updateSysRoleMenuByid(SysRoleMenu sysRoleMenu) { return sysRoleMenuMapper.updateSysRoleMenuByid(sysRoleMenu);}

            /**
            *  新增表sys_role_menu信息
            *  @param sysRoleMenu
            */
            @Override
            public Integer insertSysRoleMenu(SysRoleMenu sysRoleMenu) { return sysRoleMenuMapper.insertSysRoleMenu(sysRoleMenu);}
    }
