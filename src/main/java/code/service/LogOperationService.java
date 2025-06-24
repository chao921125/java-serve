package service;

import entity.LogOperation;
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
    public interface LogOperationService extends IService<LogOperation> {
    /**
    *  查询表log_operation所有信息
    */
    List<LogOperation> selectAllLogOperation();

            /**
            *  根据主键id查询表log_operation信息
            *  @param id
            */
            LogOperation selectLogOperationByid(@Param("id") Long id);

    /**
    *  根据条件查询表log_operation信息
    *  @param logOperation
    */
    List<LogOperation> selectLogOperationByCondition(LogOperation logOperation);

            /**
            *  根据主键id查询表log_operation信息
            *  @param id
            */
            Integer deleteLogOperationByid(@Param("id") Long id);

            /**
            *  根据主键id更新表log_operation信息
            *  @param logOperation
            */
            Integer updateLogOperationByid(LogOperation logOperation);

            /**
            *  新增表log_operation信息
            *  @param logOperation
            */
            Integer insertLogOperation(LogOperation logOperation);
    }
