// AI生成
package com.cc.core.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.sys.SysApprovalInstance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * SysApprovalInstance Mapper
 */
@Mapper
public interface SysApprovalInstanceMapper extends BaseMapper<SysApprovalInstance> {

    /**
     * 查询我的已审实例（通过审批记录关联）
     */
    @Select("SELECT DISTINCT i.* FROM sys_approval_instance i " +
            "INNER JOIN sys_approval_record r ON r.instance_id = i.id " +
            "WHERE r.approver_id = #{userId} AND i.deleted = 0 AND r.deleted = 0 " +
            "ORDER BY i.completed_time DESC NULLS LAST")
    List<SysApprovalInstance> selectMyDone(@Param("userId") Long userId);
}
