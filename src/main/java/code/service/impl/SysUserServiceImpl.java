package service.impl;

import entity.SysUser;
import java.util.List;
import mapper.SysUserMapper;
    import service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * 用户表 服务实现类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
@Service
    public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    /**
    *  查询表sys_user所有信息
    */
    @Override
    public List<SysUser> selectAllSysUser() { return sysUserMapper.selectAllSysUser();}

            /**
            *  根据主键id查询表sys_user信息
            *  @param id
            */
            @Override
            public SysUser selectSysUserByid(@Param("id") Long id) { return sysUserMapper.selectSysUserByid(id);}

    /**
    *  根据条件查询表sys_user信息
    *  @param sysUser
    */
    @Override
    public List<SysUser> selectSysUserByCondition(SysUser sysUser) { return sysUserMapper.selectSysUserByCondition(sysUser);}

            /**
            *  根据主键id查询表sys_user信息
            *  @param id
            */
            @Override
            public Integer deleteSysUserByid(@Param("id") Long id) { return sysUserMapper.deleteSysUserByid(id);}

            /**
            *  根据主键id更新表sys_user信息
            *  @param sysUser
            */
            @Override
            public Integer updateSysUserByid(SysUser sysUser) { return sysUserMapper.updateSysUserByid(sysUser);}

            /**
            *  新增表sys_user信息
            *  @param sysUser
            */
            @Override
            public Integer insertSysUser(SysUser sysUser) { return sysUserMapper.insertSysUser(sysUser);}
    }
