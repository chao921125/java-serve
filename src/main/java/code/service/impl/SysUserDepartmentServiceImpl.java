package service.impl;

import entity.SysUserDepartment;
import java.util.List;
import mapper.SysUserDepartmentMapper;
    import service.SysUserDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * 用户部门 用户1-1 部门 服务实现类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
@Service
    public class SysUserDepartmentServiceImpl extends ServiceImpl<SysUserDepartmentMapper, SysUserDepartment> implements SysUserDepartmentService {
    @Resource
    private SysUserDepartmentMapper sysUserDepartmentMapper;

    /**
    *  查询表sys_user_department所有信息
    */
    @Override
    public List<SysUserDepartment> selectAllSysUserDepartment() { return sysUserDepartmentMapper.selectAllSysUserDepartment();}

            /**
            *  根据主键id查询表sys_user_department信息
            *  @param id
            */
            @Override
            public SysUserDepartment selectSysUserDepartmentByid(@Param("id") Long id) { return sysUserDepartmentMapper.selectSysUserDepartmentByid(id);}

    /**
    *  根据条件查询表sys_user_department信息
    *  @param sysUserDepartment
    */
    @Override
    public List<SysUserDepartment> selectSysUserDepartmentByCondition(SysUserDepartment sysUserDepartment) { return sysUserDepartmentMapper.selectSysUserDepartmentByCondition(sysUserDepartment);}

            /**
            *  根据主键id查询表sys_user_department信息
            *  @param id
            */
            @Override
            public Integer deleteSysUserDepartmentByid(@Param("id") Long id) { return sysUserDepartmentMapper.deleteSysUserDepartmentByid(id);}

            /**
            *  根据主键id更新表sys_user_department信息
            *  @param sysUserDepartment
            */
            @Override
            public Integer updateSysUserDepartmentByid(SysUserDepartment sysUserDepartment) { return sysUserDepartmentMapper.updateSysUserDepartmentByid(sysUserDepartment);}

            /**
            *  新增表sys_user_department信息
            *  @param sysUserDepartment
            */
            @Override
            public Integer insertSysUserDepartment(SysUserDepartment sysUserDepartment) { return sysUserDepartmentMapper.insertSysUserDepartment(sysUserDepartment);}
    }
