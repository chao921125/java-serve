package service.impl;

import entity.LogOperation;
import java.util.List;
import mapper.LogOperationMapper;
    import service.LogOperationService;
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
    public class LogOperationServiceImpl extends ServiceImpl<LogOperationMapper, LogOperation> implements LogOperationService {
    @Resource
    private LogOperationMapper logOperationMapper;

    /**
    *  查询表log_operation所有信息
    */
    @Override
    public List<LogOperation> selectAllLogOperation() { return logOperationMapper.selectAllLogOperation();}

            /**
            *  根据主键id查询表log_operation信息
            *  @param id
            */
            @Override
            public LogOperation selectLogOperationByid(@Param("id") Long id) { return logOperationMapper.selectLogOperationByid(id);}

    /**
    *  根据条件查询表log_operation信息
    *  @param logOperation
    */
    @Override
    public List<LogOperation> selectLogOperationByCondition(LogOperation logOperation) { return logOperationMapper.selectLogOperationByCondition(logOperation);}

            /**
            *  根据主键id查询表log_operation信息
            *  @param id
            */
            @Override
            public Integer deleteLogOperationByid(@Param("id") Long id) { return logOperationMapper.deleteLogOperationByid(id);}

            /**
            *  根据主键id更新表log_operation信息
            *  @param logOperation
            */
            @Override
            public Integer updateLogOperationByid(LogOperation logOperation) { return logOperationMapper.updateLogOperationByid(logOperation);}

            /**
            *  新增表log_operation信息
            *  @param logOperation
            */
            @Override
            public Integer insertLogOperation(LogOperation logOperation) { return logOperationMapper.insertLogOperation(logOperation);}
    }
