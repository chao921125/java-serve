// AI生成
package com.cc.app.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cc.core.entity.SysUser;
import com.cc.core.entity.SysUserRole;
import com.cc.core.entity.sys.SysApprovalNode;
import com.cc.core.mapper.SysUserMapper;
import com.cc.core.mapper.SysUserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 审批人解析器
 * 根据节点的 approverType 和 approverValue 解析出实际审批人ID列表
 */
@Component
@RequiredArgsConstructor
public class ApprovalApproverResolver {

    private final SysUserMapper userMapper;
    private final SysUserRoleMapper userRoleMapper;

    /**
     * 解析节点的审批人ID列表
     */
    public List<Long> resolveApprovers(SysApprovalNode node, Long applicantId) {
        if (node.getApproverValue() == null || node.getApproverValue().isBlank()) {
            return List.of();
        }

        String type = node.getApproverType();
        String value = node.getApproverValue();

        return switch (type == null ? "USER" : type) {
            case "USER" -> parseUserIds(value);
            case "ROLE" -> resolveByRole(value);
            case "DEPARTMENT_LEADER" -> resolveByDeptLeader(applicantId);
            case "CUSTOM" -> parseUserIds(value);
            default -> parseUserIds(value);
        };
    }

    /**
     * 判断用户是否为节点的审批人
     */
    public boolean isApprover(SysApprovalNode node, Long userId, Long applicantId) {
        List<Long> approvers = resolveApprovers(node, applicantId);
        return approvers.contains(userId);
    }

    /**
     * 解析逗号分隔的用户ID
     */
    private List<Long> parseUserIds(String value) {
        List<Long> result = new ArrayList<>();
        for (String v : value.split(",")) {
            try {
                result.add(Long.parseLong(v.trim()));
            } catch (NumberFormatException ignored) {
            }
        }
        return result;
    }

    /**
     * 按角色解析：查询拥有该角色的所有用户
     */
    private List<Long> resolveByRole(String roleIds) {
        List<Long> roleIdList = parseUserIds(roleIds);
        if (roleIdList.isEmpty()) {
            return List.of();
        }
        List<SysUserRole> userRoles = userRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>()
                        .in(SysUserRole::getRoleId, roleIdList)
        );
        return userRoles.stream()
                .map(SysUserRole::getUserId)
                .distinct()
                .toList();
    }

    /**
     * 按部门负责人解析：查询申请人所在部门的负责人
     */
    private List<Long> resolveByDeptLeader(Long applicantId) {
        if (applicantId == null) {
            return List.of();
        }
        SysUser user = userMapper.selectById(applicantId);
        if (user == null || user.getDeptId() == null) {
            return List.of();
        }
        // 查询同部门中 status=0 的活跃用户作为负责人
        List<SysUser> deptUsers = userMapper.selectList(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getDeptId, user.getDeptId())
                        .eq(SysUser::getStatus, 0)
                        .orderByAsc(SysUser::getId)
        );
        if (deptUsers.isEmpty()) {
            return List.of();
        }
        // 返回部门负责人（第一个用户）
        return List.of(deptUsers.get(0).getId());
    }
}