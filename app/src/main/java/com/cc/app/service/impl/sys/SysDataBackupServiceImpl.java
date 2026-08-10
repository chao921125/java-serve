package com.cc.app.service.impl.sys;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sys.SysDataBackup;
import com.cc.core.mapper.sys.SysDataBackupMapper;
import com.cc.core.service.sys.SysDataBackupService;
import org.springframework.stereotype.Service;

/**
 * SysDataBackup 服务实现
 */
@Service
public class SysDataBackupServiceImpl extends ServiceImpl<SysDataBackupMapper, SysDataBackup> implements SysDataBackupService {

    @Override
    public void restore(Long backupId) {
        SysDataBackup backup = getById(backupId);
        if (backup == null) throw new RuntimeException("备份记录不存在");
        // TODO: 实现备份恢复逻辑
    }

    @Override
    public String download(Long backupId) {
        SysDataBackup backup = getById(backupId);
        if (backup == null) throw new RuntimeException("备份记录不存在");
        return backup.getBackupFile();
    }
}
