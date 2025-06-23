package com.cc.server.service.job;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.job.SysJob;
import java.util.List;

public interface SysJobService extends IService<SysJob> {
    List<SysJob> listAll();
    SysJob getById(Long id);
    boolean add(SysJob job);
    boolean update(SysJob job);
    boolean remove(Long id);
    boolean pause(Long id);
    boolean resume(Long id);
    boolean runOnce(Long id);
} 