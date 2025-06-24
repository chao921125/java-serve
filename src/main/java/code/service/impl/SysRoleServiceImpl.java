package service.impl;

import entity.SysRole;
import java.util.List;
import mapper.SysRoleMapper;
    import service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * 角色表 服务实现类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
@Service
    public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
    *  查询表sys_role所有信息
    */
    @Override
    public List<SysRole> selectAllSysRole() { return sysRoleMapper.selectAllSysRole();}

            /**
            *  根据主键id查询表sys_role信息
            *  @param id
            */
            @Override
            public SysRole selectSysRoleByid(@Param("id") Long id) { return sysRoleMapper.selectSysRoleByid(id);}

    /**
    *  根据条件查询表sys_role信息
    *  @param sysRole
    */
    @Override
    public List<SysRole> selectSysRoleByCondition(SysRole sysRole) { return sysRoleMapper.selectSysRoleByCondition(sysRole);}

            /**
            *  根据主键id查询表sys_role信息
            *  @param id
            */
            @Override
            public Integer deleteSysRoleByid(@Param("id") Long id) { return sysRoleMapper.deleteSysRoleByid(id);}

            /**
            *  根据主键id更新表sys_role信息
            *  @param sysRole
            */
            @Override
            public Integer updateSysRoleByid(SysRole sysRole) { return sysRoleMapper.updateSysRoleByid(sysRole);}

            /**
            *  新增表sys_role信息
            *  @param sysRole
            */
            @Override
            public Integer insertSysRole(SysRole sysRole) { return sysRoleMapper.insertSysRole(sysRole);}
    }
