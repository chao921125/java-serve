package mapper;

import entity.SysUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* <p>
    * 用户表 Mapper 接口
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
    public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
    *  查询表sys_user所有信息
    */
    List<SysUser> selectAllSysUser();

            /**
            *  根据主键id查询表sys_user信息
            *  @param id
            */
            SysUser selectSysUserByid(@Param("id") Long id);

    /**
    *  根据条件查询表sys_user信息
    *  @param sysUser
    */
    List<SysUser> selectSysUserByCondition(SysUser sysUser);

            /**
            *  根据主键id查询表sys_user信息
            *  @param id
            */
            Integer deleteSysUserByid(@Param("id") Long id);

            /**
            *  根据主键id更新表sys_user信息
            *  @param sysUser
            */
            Integer updateSysUserByid(SysUser sysUser);

            /**
            *  新增表sys_user信息
            *  @param sysUser
            */
            Integer insertSysUser(SysUser sysUser);

    }
