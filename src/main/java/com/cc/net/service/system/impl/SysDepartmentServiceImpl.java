package com.cc.net.service.system.impl;

import com.cc.net.entity.system.SysDepartment;
import com.cc.net.mapper.system.SysDepartmentMapper;
import com.cc.net.service.system.SysDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2023-55-27 14:09:306
 */
@Service
public class SysDepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements SysDepartmentService {

    @Override
    public ArrayList<SysDepartment> getDeptAll() {
//        return sysDepartmentMapper.selectList(1, 2);
        return new ArrayList<SysDepartment>();
    }
}
