package com.cc.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends BaseEntity {

    /** 用户名 */
    private String userName;

    /** 密码 */
    private String password;

    /** 昵称 */
    private String nickName;

    /** 真实姓名 */
    private String realName;

    /** 头像 */
    private String avatar;

    /** 邮箱 */
    private String email;

    /** 手机号 */
    private String phone;

    /** 性别 0-男 1-女 */
    private Integer sex;

    /** 年龄 */
    private Integer age;

    /** 地址 */
    private String address;

    /** 状态 0-正常 1-停用 */
    private Integer status;

    /** 最后登录 IP */
    private String loginIp;

    /** 最后登录地址 */
    private String loginAddress;

    /** 最后登录信息 */
    private String loginInfo;

    /** 最后登录时间 */
    private String loginTime;

    /** 密码更新时间 */
    private String pwdUpdateTime;

    /** 部门 ID（冗余字段，方便查询） */
    private Long deptId;
}
