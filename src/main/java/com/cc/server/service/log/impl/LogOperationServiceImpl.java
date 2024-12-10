package com.cc.server.service.log.impl;

import com.cc.server.model.log.entity.LogOperation;
import com.cc.server.mapper.log.LogOperationMapper;
import com.cc.server.service.log.LogOperationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Service
public class LogOperationServiceImpl extends ServiceImpl<LogOperationMapper, LogOperation> implements LogOperationService {

}
