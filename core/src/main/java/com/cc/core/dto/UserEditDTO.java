package com.cc.core.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 修改用户请求
 */
@Data
public class UserEditDTO {

    @NotNull(message = "用户ID不能为空")
    private Long id;

    private String nickName;
    private String realName;
    private String email;
    private String phone;
    private Integer sex;
    private Integer age;
    private String address;
    private Long deptId;
    private List<Long> roleIds;
    private List<Long> postIds;
    private String remark;
    private Integer status;
}
