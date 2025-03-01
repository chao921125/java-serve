package com.cc.server.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.server.entity.system.SysUserDepartment;
import com.cc.server.mapper.system.SysUserDepartmentMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cc.server.service.system.SysUserDepartmentService;

import java.util.List;

/**
 * <p>
 * 用户部门 用户1-1 部门 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@Service
public class SysUserDepartmentServiceImpl extends ServiceImpl<SysUserDepartmentMapper, SysUserDepartment> implements SysUserDepartmentService {
  @Autowired
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
   public SysUserDepartment selectSysUserDepartmentById(@Param("id") Long id) { return sysUserDepartmentMapper.selectSysUserDepartmentById(id);}

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
   public Integer deleteSysUserDepartmentById(@Param("id") Long id) { return sysUserDepartmentMapper.deleteSysUserDepartmentById(id);}

   /**
   *  根据主键id更新表sys_user_department信息
   *  @param sysUserDepartment
   */
   @Override
   public Integer updateSysUserDepartmentById(SysUserDepartment sysUserDepartment) { return sysUserDepartmentMapper.updateSysUserDepartmentById(sysUserDepartment);}

   /**
   *  新增表sys_user_department信息
   *  @param sysUserDepartment
   */
   @Override
   public Integer insertSysUserDepartment(SysUserDepartment sysUserDepartment) { return sysUserDepartmentMapper.insertSysUserDepartment(sysUserDepartment);}
}
