package com.cc.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志
 */
@Data
@TableName("log_operation")
public class LogOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 操作模块 */
    private String title;

    /** 业务类型 */
    private Integer businessType;

    /** 操作用户 ID */
    private Long userId;

    /** 操作用户名 */
    private String userName;

    /** IP 地址 */
    private String ip;

    /** 真实 IP */
    private String ipReal;

    /** 操作地址 */
    private String address;

    /** 操作系统/浏览器 */
    private String system;

    /** 操作状态 0-成功 1-失败 */
    private Integer status;

    /** 请求 URL */
    private String url;

    /** 请求方法 */
    private String method;

    /** 请求方式 GET/POST/PUT/DELETE */
    private String methodType;

    /** 提示消息 */
    private String message;

    /** 异常信息 */
    private String exceptionMsg;

    /** 请求参数 */
    private String params;

    /** 响应结果 */
    private String result;

    /** 耗时（毫秒） */
    private Long costTime;

    /** 操作时间 */
    private LocalDateTime operTime;
}
