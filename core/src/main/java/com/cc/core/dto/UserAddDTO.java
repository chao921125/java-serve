package com.cc.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 新增用户请求
 */
@Data
public class UserAddDTO {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度3-20个字符")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 50, message = "密码长度6-50个字符")
    private String password;

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
}
