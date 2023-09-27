package com.cc.javaserve.domain.system;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author cc
 * @since 2023-28-27 10:09:265
 */
@Getter
@Setter
@TableName("sys_user")
public class SysUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 用户登陆名
     */
    private String userName;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别（0女 1男 2未知）
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 加密
     */
    private String salt;

    /**
     * 最后登陆ip
     */
    private String loginIp;

    /**
     * 最后登陆地址
     */
    private String loginAddress;

    /**
     * 最后登陆设备
     */
    private String loginInfo;

    /**
     * 最后登陆时间
     */
    private LocalDateTime loginTime;

    /**
     * 密码最后修改时间
     */
    private LocalDateTime pwdUpdateTime;

    /**
     * 删除标识（0删除 1未删除）
     */
    private String delFlag;

    /**
     * 状态（1正常 0停用）
     */
    private String status;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;
}
