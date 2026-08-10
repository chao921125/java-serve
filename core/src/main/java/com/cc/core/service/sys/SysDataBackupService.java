package com.cc.core.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.sys.SysDataBackup;

/**
 * SysDataBackup 服务接口
 */
public interface SysDataBackupService extends IService<SysDataBackup> {

    /**
     * 恢复备份
     */
    void restore(Long backupId);

    /**
     * 下载备份文件
     */
    String download(Long backupId);
}
