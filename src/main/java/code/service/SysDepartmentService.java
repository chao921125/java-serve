package service;

import entity.SysDepartment;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
    * 部门表 服务类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
    public interface SysDepartmentService extends IService<SysDepartment> {
    /**
    *  查询表sys_department所有信息
    */
    List<SysDepartment> selectAllSysDepartment();

            /**
            *  根据主键id查询表sys_department信息
            *  @param id
            */
            SysDepartment selectSysDepartmentByid(@Param("id") Long id);

    /**
    *  根据条件查询表sys_department信息
    *  @param sysDepartment
    */
    List<SysDepartment> selectSysDepartmentByCondition(SysDepartment sysDepartment);

            /**
            *  根据主键id查询表sys_department信息
            *  @param id
            */
            Integer deleteSysDepartmentByid(@Param("id") Long id);

            /**
            *  根据主键id更新表sys_department信息
            *  @param sysDepartment
            */
            Integer updateSysDepartmentByid(SysDepartment sysDepartment);

            /**
            *  新增表sys_department信息
            *  @param sysDepartment
            */
            Integer insertSysDepartment(SysDepartment sysDepartment);
    }
