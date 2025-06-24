package service.impl;

import entity.SysUserRole;
import java.util.List;
import mapper.SysUserRoleMapper;
    import service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * 用户角色 用户N-1角色 服务实现类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
@Service
    public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    /**
    *  查询表sys_user_role所有信息
    */
    @Override
    public List<SysUserRole> selectAllSysUserRole() { return sysUserRoleMapper.selectAllSysUserRole();}

            /**
            *  根据主键id查询表sys_user_role信息
            *  @param id
            */
            @Override
            public SysUserRole selectSysUserRoleByid(@Param("id") Long id) { return sysUserRoleMapper.selectSysUserRoleByid(id);}

    /**
    *  根据条件查询表sys_user_role信息
    *  @param sysUserRole
    */
    @Override
    public List<SysUserRole> selectSysUserRoleByCondition(SysUserRole sysUserRole) { return sysUserRoleMapper.selectSysUserRoleByCondition(sysUserRole);}

            /**
            *  根据主键id查询表sys_user_role信息
            *  @param id
            */
            @Override
            public Integer deleteSysUserRoleByid(@Param("id") Long id) { return sysUserRoleMapper.deleteSysUserRoleByid(id);}

            /**
            *  根据主键id更新表sys_user_role信息
            *  @param sysUserRole
            */
            @Override
            public Integer updateSysUserRoleByid(SysUserRole sysUserRole) { return sysUserRoleMapper.updateSysUserRoleByid(sysUserRole);}

            /**
            *  新增表sys_user_role信息
            *  @param sysUserRole
            */
            @Override
            public Integer insertSysUserRole(SysUserRole sysUserRole) { return sysUserRoleMapper.insertSysUserRole(sysUserRole);}
    }
