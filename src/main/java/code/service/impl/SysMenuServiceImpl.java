package service.impl;

import entity.SysMenu;
import java.util.List;
import mapper.SysMenuMapper;
    import service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * 菜单表 服务实现类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
@Service
    public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
    *  查询表sys_menu所有信息
    */
    @Override
    public List<SysMenu> selectAllSysMenu() { return sysMenuMapper.selectAllSysMenu();}

            /**
            *  根据主键id查询表sys_menu信息
            *  @param id
            */
            @Override
            public SysMenu selectSysMenuByid(@Param("id") Long id) { return sysMenuMapper.selectSysMenuByid(id);}

    /**
    *  根据条件查询表sys_menu信息
    *  @param sysMenu
    */
    @Override
    public List<SysMenu> selectSysMenuByCondition(SysMenu sysMenu) { return sysMenuMapper.selectSysMenuByCondition(sysMenu);}

            /**
            *  根据主键id查询表sys_menu信息
            *  @param id
            */
            @Override
            public Integer deleteSysMenuByid(@Param("id") Long id) { return sysMenuMapper.deleteSysMenuByid(id);}

            /**
            *  根据主键id更新表sys_menu信息
            *  @param sysMenu
            */
            @Override
            public Integer updateSysMenuByid(SysMenu sysMenu) { return sysMenuMapper.updateSysMenuByid(sysMenu);}

            /**
            *  新增表sys_menu信息
            *  @param sysMenu
            */
            @Override
            public Integer insertSysMenu(SysMenu sysMenu) { return sysMenuMapper.insertSysMenu(sysMenu);}
    }
