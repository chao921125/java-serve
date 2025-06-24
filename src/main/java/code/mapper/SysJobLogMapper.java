package mapper;

import entity.SysJobLog;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* <p>
    *  Mapper 接口
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
    public interface SysJobLogMapper extends BaseMapper<SysJobLog> {
    /**
    *  查询表sys_job_log所有信息
    */
    List<SysJobLog> selectAllSysJobLog();

            /**
            *  根据主键id查询表sys_job_log信息
            *  @param id
            */
            SysJobLog selectSysJobLogByid(@Param("id") Long id);

    /**
    *  根据条件查询表sys_job_log信息
    *  @param sysJobLog
    */
    List<SysJobLog> selectSysJobLogByCondition(SysJobLog sysJobLog);

            /**
            *  根据主键id查询表sys_job_log信息
            *  @param id
            */
            Integer deleteSysJobLogByid(@Param("id") Long id);

            /**
            *  根据主键id更新表sys_job_log信息
            *  @param sysJobLog
            */
            Integer updateSysJobLogByid(SysJobLog sysJobLog);

            /**
            *  新增表sys_job_log信息
            *  @param sysJobLog
            */
            Integer insertSysJobLog(SysJobLog sysJobLog);

    }
