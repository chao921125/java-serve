package service;

import entity.SysUserDepartment;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
    * 用户部门 用户1-1 部门 服务类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
    public interface SysUserDepartmentService extends IService<SysUserDepartment> {
    /**
    *  查询表sys_user_department所有信息
    */
    List<SysUserDepartment> selectAllSysUserDepartment();

            /**
            *  根据主键id查询表sys_user_department信息
            *  @param id
            */
            SysUserDepartment selectSysUserDepartmentByid(@Param("id") Long id);

    /**
    *  根据条件查询表sys_user_department信息
    *  @param sysUserDepartment
    */
    List<SysUserDepartment> selectSysUserDepartmentByCondition(SysUserDepartment sysUserDepartment);

            /**
            *  根据主键id查询表sys_user_department信息
            *  @param id
            */
            Integer deleteSysUserDepartmentByid(@Param("id") Long id);

            /**
            *  根据主键id更新表sys_user_department信息
            *  @param sysUserDepartment
            */
            Integer updateSysUserDepartmentByid(SysUserDepartment sysUserDepartment);

            /**
            *  新增表sys_user_department信息
            *  @param sysUserDepartment
            */
            Integer insertSysUserDepartment(SysUserDepartment sysUserDepartment);
    }
