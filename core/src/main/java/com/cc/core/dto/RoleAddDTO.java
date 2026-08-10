package com.cc.core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 新增/修改角色请求
 */
@Data
public class RoleAddDTO {

    private Long id;

    @NotBlank(message = "角色名称不能为空")
    private String name;

    @NotBlank(message = "角色编码不能为空")
    private String code;

    private Integer sort;
    private Integer dataScope;
    private List<Long> menuIds;
    private List<Long> deptIds;
    private String remark;
}
