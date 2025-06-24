package service;

import entity.SysJob;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
    *  服务类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
    public interface SysJobService extends IService<SysJob> {
    /**
    *  查询表sys_job所有信息
    */
    List<SysJob> selectAllSysJob();

            /**
            *  根据主键id查询表sys_job信息
            *  @param id
            */
            SysJob selectSysJobByid(@Param("id") Long id);

    /**
    *  根据条件查询表sys_job信息
    *  @param sysJob
    */
    List<SysJob> selectSysJobByCondition(SysJob sysJob);

            /**
            *  根据主键id查询表sys_job信息
            *  @param id
            */
            Integer deleteSysJobByid(@Param("id") Long id);

            /**
            *  根据主键id更新表sys_job信息
            *  @param sysJob
            */
            Integer updateSysJobByid(SysJob sysJob);

            /**
            *  新增表sys_job信息
            *  @param sysJob
            */
            Integer insertSysJob(SysJob sysJob);
    }
