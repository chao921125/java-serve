package com.cc.serve.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * log_operation 
 * </p>
 * 
 * @author cc
 * @since 2024-11-20 23:04:58
 */

@TableName("log_operation")
@Schema(name = "LogOperation对象", description = "")
public class LogOperation implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户 id")
    @TableField("user_id")
    private String userId;

    @Schema(description = "用户名")
    @TableField("user_name")
    private String userName;

    @Schema(description = "登录 IP")
    @TableField("ip")
    private String ip;

    @Schema(description = "真实IP")
    @TableField("ip_real")
    private String ipReal;

    @Schema(description = "操作时间")
    @TableField("oper_time")
    private Date operTime;

    @Schema(description = "地址")
    @TableField("address")
    private String address;

    @Schema(description = "设备信息")
    @TableField("system")
    private String system;

    @Schema(description = "登录状态（0 失败，1 成功）")
    @TableField("status")
    private String status;

    @Schema(description = "操作（0 查询，1 新增，2 修改，3 删除）")
    @TableField("type")
    private String type;

    @Schema(description = "来源（0 其他，1 PC，2 手机）")
    @TableField("source")
    private String source;

    @Schema(description = "请求 URL")
    @TableField("url")
    private String url;

    @Schema(description = "请求方法")
    @TableField("method")
    private String method;

    @Schema(description = "请求方式：post，get......")
    @TableField("method_type")
    private String methodType;

    @Schema(description = "错误消息")
    @TableField("message")
    private String message;


    public Long getId() {
    return id;
    }

    public void setId(Long id) {
    this.id = id;
    }

    public String getUserId() {
    return userId;
    }

    public void setUserId(String userId) {
    this.userId = userId;
    }

    public String getUserName() {
    return userName;
    }

    public void setUserName(String userName) {
    this.userName = userName;
    }

    public String getIp() {
    return ip;
    }

    public void setIp(String ip) {
    this.ip = ip;
    }

    public String getIpReal() {
    return ipReal;
    }

    public void setIpReal(String ipReal) {
    this.ipReal = ipReal;
    }

    public Date getOperTime() {
    return operTime;
    }

    public void setOperTime(Date operTime) {
    this.operTime = operTime;
    }

    public String getAddress() {
    return address;
    }

    public void setAddress(String address) {
    this.address = address;
    }

    public String getSystem() {
    return system;
    }

    public void setSystem(String system) {
    this.system = system;
    }

    public String getStatus() {
    return status;
    }

    public void setStatus(String status) {
    this.status = status;
    }

    public String getType() {
    return type;
    }

    public void setType(String type) {
    this.type = type;
    }

    public String getSource() {
    return source;
    }

    public void setSource(String source) {
    this.source = source;
    }

    public String getUrl() {
    return url;
    }

    public void setUrl(String url) {
    this.url = url;
    }

    public String getMethod() {
    return method;
    }

    public void setMethod(String method) {
    this.method = method;
    }

    public String getMethodType() {
    return methodType;
    }

    public void setMethodType(String methodType) {
    this.methodType = methodType;
    }

    public String getMessage() {
    return message;
    }

    public void setMessage(String message) {
    this.message = message;
    }

    @Override
    public String toString() {
    return "LogOperation{" +
            "id = " + id +
            ", userId = " + userId +
            ", userName = " + userName +
            ", ip = " + ip +
            ", ipReal = " + ipReal +
            ", operTime = " + operTime +
            ", address = " + address +
            ", system = " + system +
            ", status = " + status +
            ", type = " + type +
            ", source = " + source +
            ", url = " + url +
            ", method = " + method +
            ", methodType = " + methodType +
            ", message = " + message +
    "}";
    }
}