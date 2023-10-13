package com.cc.net.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author cc
 * @since 2023-47-13 15:10:984
 */
@Getter
@Setter
@TableName("sys_user")
@Schema(description = "SysUser对象")
public class SysUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @Schema(description = "部门id")
    @TableField("dept_id")
    private Long deptId;

    @Schema(description = "用户登陆名")
    @TableField("user_name")
    private String userName;

    @Schema(description = "登陆密码")
    @TableField("password")
    private String password;

    @Schema(description = "昵称")
    @TableField("nick_name")
    private String nickName;

    @Schema(description = "邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "手机号")
    @TableField("phone")
    private String phone;

    @Schema(description = "性别（0女 1男 2未知）")
    @TableField("sex")
    private String sex;

    @Schema(description = "头像")
    @TableField("avatar")
    private String avatar;

    @Schema(description = "加密")
    @TableField("salt")
    private String salt;

    @Schema(description = "最后登陆ip")
    @TableField("login_ip")
    private String loginIp;

    @Schema(description = "最后登陆地址")
    @TableField("login_address")
    private String loginAddress;

    @Schema(description = "最后登陆设备")
    @TableField("login_info")
    private String loginInfo;

    @Schema(description = "最后登陆时间")
    @TableField("login_time")
    private Date loginTime;

    @Schema(description = "密码最后修改时间")
    @TableField("pwd_update_time")
    private Date pwdUpdateTime;

    @Schema(description = "删除标识（0删除 1未删除）")
    @TableField("del_flag")
    private String delFlag;

    @Schema(description = "状态（1正常 0停用）")
    @TableField("status")
    private String status;

    @Schema(description = "创建人")
    @TableField("create_by")
    private String createBy;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @Schema(description = "修改人")
    @TableField("update_by")
    private String updateBy;

    @Schema(description = "修改时间")
    @TableField("update_time")
    private Date updateTime;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
