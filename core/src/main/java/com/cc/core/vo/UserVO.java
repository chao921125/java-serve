package com.cc.core.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户视图对象
 */
@Data
public class UserVO {
    private Long id;
    private String userName;
    private String nickName;
    private String realName;
    private String avatar;
    private String email;
    private String phone;
    private Integer sex;
    private Integer age;
    private String address;
    private Integer status;
    private String loginIp;
    private String loginTime;
    private Long deptId;
    private String deptName;
    private List<Long> roleIds;
    private List<String> roleNames;
    private List<Long> postIds;
    private List<String> postNames;
    private String createBy;
    private LocalDateTime createTime;
    private String remark;
}
