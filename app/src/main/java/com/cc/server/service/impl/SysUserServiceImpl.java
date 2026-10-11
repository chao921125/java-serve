// AI生成
package com.cc.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.SysDepartment;
import com.cc.core.entity.SysPost;
import com.cc.core.entity.SysRole;
import com.cc.core.entity.SysUser;
import com.cc.core.mapper.SysRoleMapper;
import com.cc.core.mapper.SysUserMapper;
import com.cc.core.service.SysDepartmentService;
import com.cc.core.service.SysPostService;
import com.cc.core.service.SysUserDepartmentService;
import com.cc.core.service.SysUserPostService;
import com.cc.core.service.SysUserRoleService;
import com.cc.core.service.SysUserService;
import com.cc.core.vo.UserVO;
import com.cc.framework.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserRoleService userRoleService;
    private final SysUserDepartmentService userDeptService;
    private final SysUserPostService userPostService;
    private final SysRoleMapper roleMapper;
    private final SysDepartmentService deptService;
    private final SysPostService postService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SysUser getByUsername(String username) {
        return baseMapper.selectByUsername(username);
    }

    @Override
    @Transactional
    public boolean addUser(SysUser user, List<Long> roleIds, List<Long> postIds, Long deptId) {
        // 校验用户名唯一
        if (baseMapper.selectByUsername(user.getUserName()) != null) {
            throw ServiceException.badRequest("用户名已存在");
        }
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(0);
        user.setPwdUpdateTime(LocalDateTime.now().toString());
        save(user);
        // 保存角色关联
        if (roleIds != null && !roleIds.isEmpty()) {
            userRoleService.batchInsert(user.getId(), roleIds);
        }
        // 保存岗位关联
        if (postIds != null && !postIds.isEmpty()) {
            userPostService.batchInsert(user.getId(), postIds);
        }
        // 保存部门关联
        if (deptId != null) {
            userDeptService.saveOrUpdateDept(user.getId(), deptId);
            user.setDeptId(deptId);
            updateById(user);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateUser(SysUser user, List<Long> roleIds, List<Long> postIds, Long deptId) {
        SysUser existing = getById(user.getId());
        if (existing == null) {
            throw ServiceException.notFound("用户不存在");
        }
        // 不允许修改密码字段（走专用接口）
        user.setPassword(null);
        updateById(user);
        // 更新角色关联
        if (roleIds != null) {
            userRoleService.deleteByUserId(user.getId());
            if (!roleIds.isEmpty()) {
                userRoleService.batchInsert(user.getId(), roleIds);
            }
        }
        // 更新岗位关联
        if (postIds != null) {
            userPostService.deleteByUserId(user.getId());
            if (!postIds.isEmpty()) {
                userPostService.batchInsert(user.getId(), postIds);
            }
        }
        // 更新部门关联
        if (deptId != null) {
            userDeptService.saveOrUpdateDept(user.getId(), deptId);
            user.setDeptId(deptId);
            updateById(user);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean resetPassword(Long userId, String newPassword) {
        SysUser user = getById(userId);
        if (user == null) {
            throw ServiceException.notFound("用户不存在");
        }
        SysUser update = new SysUser();
        update.setId(userId);
        update.setPassword(passwordEncoder.encode(newPassword));
        update.setPwdUpdateTime(LocalDateTime.now().toString());
        return updateById(update);
    }

    @Override
    @Transactional
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        SysUser user = getById(userId);
        if (user == null) {
            throw ServiceException.notFound("用户不存在");
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw ServiceException.badRequest("原密码不正确");
        }
        SysUser update = new SysUser();
        update.setId(userId);
        update.setPassword(passwordEncoder.encode(newPassword));
        update.setPwdUpdateTime(LocalDateTime.now().toString());
        return updateById(update);
    }

    @Override
    @Transactional
    public boolean assignRoles(Long userId, List<Long> roleIds) {
        if (getById(userId) == null) {
            throw ServiceException.notFound("用户不存在");
        }
        userRoleService.deleteByUserId(userId);
        if (roleIds != null && !roleIds.isEmpty()) {
            userRoleService.batchInsert(userId, roleIds);
        }
        return true;
    }

    /**
     * 分页查询用户列表
     */
    public Page<SysUser> selectUserPage(Integer pageNum, Integer pageSize, String userName, Integer status, Long deptId) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        List<SysUser> list = baseMapper.selectUserPage(userName, status, deptId);
        page.setRecords(list);
        page.setTotal(list.size());
        return page;
    }

    /**
     * 查询用户详情（含角色、部门、岗位信息）
     */
    @Override
    public UserVO getUserDetail(Long userId) {
        SysUser user = getById(userId);
        if (user == null) {
            throw ServiceException.notFound("用户不存在");
        }
        return enrichUserVO(user);
    }

    /**
     * 将 SysUser 转换为 UserVO 并填充角色、部门、岗位关联数据
     */
    @Override
    public UserVO enrichUserVO(SysUser user) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        // 查询角色
        List<SysRole> roles = roleMapper.selectRolesByUserId(user.getId());
        vo.setRoleIds(roles.stream().map(SysRole::getId).collect(Collectors.toList()));
        vo.setRoleNames(roles.stream().map(SysRole::getName).collect(Collectors.toList()));
        // 查询岗位
        List<Long> postIds = userPostService.getPostIdsByUserId(user.getId());
        vo.setPostIds(postIds);
        if (postIds != null && !postIds.isEmpty()) {
            List<SysPost> posts = postService.listByIds(postIds);
            vo.setPostNames(posts.stream().map(SysPost::getName).collect(Collectors.toList()));
        }
        // 查询部门
        Long deptId = userDeptService.getDeptIdByUserId(user.getId());
        vo.setDeptId(deptId);
        if (deptId != null) {
            SysDepartment dept = deptService.getById(deptId);
            if (dept != null) {
                vo.setDeptName(dept.getName());
            }
        }
        return vo;
    }
}
