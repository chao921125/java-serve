package com.cc.net.service.system;

import com.cc.net.entity.system.SysDepartment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author cc
 * @since 2023-55-27 14:09:306
 */
public interface SysDepartmentService extends IService<SysDepartment> {
    public ArrayList<SysDepartment> getDeptAll();
}
