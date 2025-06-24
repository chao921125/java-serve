package service.impl;

import entity.SysJob;
import java.util.List;
import mapper.SysJobMapper;
    import service.SysJobService;
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
    public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {
    @Resource
    private SysJobMapper sysJobMapper;

    /**
    *  查询表sys_job所有信息
    */
    @Override
    public List<SysJob> selectAllSysJob() { return sysJobMapper.selectAllSysJob();}

            /**
            *  根据主键id查询表sys_job信息
            *  @param id
            */
            @Override
            public SysJob selectSysJobByid(@Param("id") Long id) { return sysJobMapper.selectSysJobByid(id);}

    /**
    *  根据条件查询表sys_job信息
    *  @param sysJob
    */
    @Override
    public List<SysJob> selectSysJobByCondition(SysJob sysJob) { return sysJobMapper.selectSysJobByCondition(sysJob);}

            /**
            *  根据主键id查询表sys_job信息
            *  @param id
            */
            @Override
            public Integer deleteSysJobByid(@Param("id") Long id) { return sysJobMapper.deleteSysJobByid(id);}

            /**
            *  根据主键id更新表sys_job信息
            *  @param sysJob
            */
            @Override
            public Integer updateSysJobByid(SysJob sysJob) { return sysJobMapper.updateSysJobByid(sysJob);}

            /**
            *  新增表sys_job信息
            *  @param sysJob
            */
            @Override
            public Integer insertSysJob(SysJob sysJob) { return sysJobMapper.insertSysJob(sysJob);}
    }
