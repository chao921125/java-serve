package com.cc.core.dto;

import lombok.Data;

import java.util.List;

/**
 * 分配菜单请求
 */
@Data
public class AssignMenusDTO {
    private List<Long> menuIds;
}
