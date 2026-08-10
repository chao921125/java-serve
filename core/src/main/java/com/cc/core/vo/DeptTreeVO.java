package com.cc.core.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门树形视图对象
 */
@Data
public class DeptTreeVO {
    private Long id;
    private Long parentId;
    private String ancestors;
    private String name;
    private Integer sort;
    private String leader;
    private String phone;
    private String email;
    private Integer status;
    private String createBy;
    private LocalDateTime createTime;
    private String remark;
    private List<DeptTreeVO> children;
}
