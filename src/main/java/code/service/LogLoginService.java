package service;

import entity.LogLogin;
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
    public interface LogLoginService extends IService<LogLogin> {
    /**
    *  查询表log_login所有信息
    */
    List<LogLogin> selectAllLogLogin();

            /**
            *  根据主键id查询表log_login信息
            *  @param id
            */
            LogLogin selectLogLoginByid(@Param("id") Long id);

    /**
    *  根据条件查询表log_login信息
    *  @param logLogin
    */
    List<LogLogin> selectLogLoginByCondition(LogLogin logLogin);

            /**
            *  根据主键id查询表log_login信息
            *  @param id
            */
            Integer deleteLogLoginByid(@Param("id") Long id);

            /**
            *  根据主键id更新表log_login信息
            *  @param logLogin
            */
            Integer updateLogLoginByid(LogLogin logLogin);

            /**
            *  新增表log_login信息
            *  @param logLogin
            */
            Integer insertLogLogin(LogLogin logLogin);
    }
