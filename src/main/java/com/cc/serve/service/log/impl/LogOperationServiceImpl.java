package com.cc.serve.service.log.impl;

import com.cc.serve.model.entity.log.LogOperation;
import com.cc.serve.mapper.log.LogOperationMapper;
import com.cc.serve.service.log.LogOperationService;
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
