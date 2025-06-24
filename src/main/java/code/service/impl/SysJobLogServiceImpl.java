package service.impl;

import entity.SysJobLog;
import java.util.List;
import mapper.SysJobLogMapper;
    import service.SysJobLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    *  服务实现类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
@Service
    public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements SysJobLogService {
    @Resource
    private SysJobLogMapper sysJobLogMapper;

    /**
    *  查询表sys_job_log所有信息
    */
    @Override
    public List<SysJobLog> selectAllSysJobLog() { return sysJobLogMapper.selectAllSysJobLog();}

            /**
            *  根据主键id查询表sys_job_log信息
            *  @param id
            */
            @Override
            public SysJobLog selectSysJobLogByid(@Param("id") Long id) { return sysJobLogMapper.selectSysJobLogByid(id);}

    /**
    *  根据条件查询表sys_job_log信息
    *  @param sysJobLog
    */
    @Override
    public List<SysJobLog> selectSysJobLogByCondition(SysJobLog sysJobLog) { return sysJobLogMapper.selectSysJobLogByCondition(sysJobLog);}

            /**
            *  根据主键id查询表sys_job_log信息
            *  @param id
            */
            @Override
            public Integer deleteSysJobLogByid(@Param("id") Long id) { return sysJobLogMapper.deleteSysJobLogByid(id);}

            /**
            *  根据主键id更新表sys_job_log信息
            *  @param sysJobLog
            */
            @Override
            public Integer updateSysJobLogByid(SysJobLog sysJobLog) { return sysJobLogMapper.updateSysJobLogByid(sysJobLog);}

            /**
            *  新增表sys_job_log信息
            *  @param sysJobLog
            */
            @Override
            public Integer insertSysJobLog(SysJobLog sysJobLog) { return sysJobLogMapper.insertSysJobLog(sysJobLog);}
    }
