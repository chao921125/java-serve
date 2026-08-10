package com.cc.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志
 */
@Data
@TableName("log_login")
public class LogLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户 ID */
    private Long userId;

    /** 用户名 */
    private String userName;

    /** IP 地址 */
    private String ip;

    /** 真实 IP */
    private String ipReal;

    /** 登录时间 */
    private LocalDateTime loginTime;

    /** 登录地址 */
    private String address;

    /** 操作系统/浏览器 */
    private String system;

    /** 登录状态 0-成功 1-失败 */
    private Integer status;

    /** 提示消息 */
    private String message;

    /** 异常信息 */
    private String exceptionMsg;
}
