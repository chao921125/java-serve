package com.cc.core.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * 当前用户信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {
    private Long userId;
    private String userName;
    private String nickName;
    private String avatar;
    private Long deptId;
    private String deptName;
    private Set<String> roles;
    private Set<String> permissions;
    private List<Long> roleIds;
}
