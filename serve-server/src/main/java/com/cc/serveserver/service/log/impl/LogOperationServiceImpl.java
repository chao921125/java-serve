package com.cc.serveserver.service.log.impl;

import com.cc.serveserver.model.entity.log.LogOperation;
import com.cc.serveserver.mapper.log.LogOperationMapper;
import com.cc.serveserver.service.log.LogOperationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cc
 * @since 2024-11-22 13:40:54
 */
@Service
public class LogOperationServiceImpl extends ServiceImpl<LogOperationMapper, LogOperation> implements LogOperationService {

}
