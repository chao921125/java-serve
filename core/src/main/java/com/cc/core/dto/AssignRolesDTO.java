package com.cc.core.dto;

import lombok.Data;

import java.util.List;

/**
 * 分配角色请求
 */
@Data
public class AssignRolesDTO {
    private List<Long> roleIds;
}
