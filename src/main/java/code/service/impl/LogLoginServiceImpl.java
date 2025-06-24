package service.impl;

import entity.LogLogin;
import java.util.List;
import mapper.LogLoginMapper;
    import service.LogLoginService;
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
    public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements LogLoginService {
    @Resource
    private LogLoginMapper logLoginMapper;

    /**
    *  查询表log_login所有信息
    */
    @Override
    public List<LogLogin> selectAllLogLogin() { return logLoginMapper.selectAllLogLogin();}

            /**
            *  根据主键id查询表log_login信息
            *  @param id
            */
            @Override
            public LogLogin selectLogLoginByid(@Param("id") Long id) { return logLoginMapper.selectLogLoginByid(id);}

    /**
    *  根据条件查询表log_login信息
    *  @param logLogin
    */
    @Override
    public List<LogLogin> selectLogLoginByCondition(LogLogin logLogin) { return logLoginMapper.selectLogLoginByCondition(logLogin);}

            /**
            *  根据主键id查询表log_login信息
            *  @param id
            */
            @Override
            public Integer deleteLogLoginByid(@Param("id") Long id) { return logLoginMapper.deleteLogLoginByid(id);}

            /**
            *  根据主键id更新表log_login信息
            *  @param logLogin
            */
            @Override
            public Integer updateLogLoginByid(LogLogin logLogin) { return logLoginMapper.updateLogLoginByid(logLogin);}

            /**
            *  新增表log_login信息
            *  @param logLogin
            */
            @Override
            public Integer insertLogLogin(LogLogin logLogin) { return logLoginMapper.insertLogLogin(logLogin);}
    }
