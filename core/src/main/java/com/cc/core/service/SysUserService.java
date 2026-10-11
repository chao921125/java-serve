// AI生成
package com.cc.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.SysUser;
import com.cc.core.vo.UserVO;

import java.util.List;

/**
 * 用户服务接口
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询
     */
    SysUser getByUsername(String username);

    /**
     * 查询用户详情（含角色、部门、岗位信息）
     */
    UserVO getUserDetail(Long userId);

    /**
     * 将 SysUser 转换为 UserVO 并填充关联数据
     */
    UserVO enrichUserVO(SysUser user);

    /**
     * 新增用户（含角色分配）
     */
    boolean addUser(SysUser user, List<Long> roleIds, List<Long> postIds, Long deptId);

    /**
     * 修改用户（含角色分配）
     */
    boolean updateUser(SysUser user, List<Long> roleIds, List<Long> postIds, Long deptId);

    /**
     * 重置密码
     */
    boolean resetPassword(Long userId, String newPassword);

    /**
     * 修改密码
     */
    boolean changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 分配角色
     */
    boolean assignRoles(Long userId, List<Long> roleIds);
}
