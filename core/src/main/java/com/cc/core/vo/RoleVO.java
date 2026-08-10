package com.cc.core.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色视图对象
 */
@Data
public class RoleVO {
    private Long id;
    private String name;
    private String code;
    private Integer sort;
    private String permissions;
    private Integer dataScope;
    private Integer status;
    private List<Long> menuIds;
    private List<Long> deptIds;
    private String createBy;
    private LocalDateTime createTime;
    private String remark;
}
