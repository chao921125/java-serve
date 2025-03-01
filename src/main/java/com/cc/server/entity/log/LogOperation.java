package com.cc.server.entity.log;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * log_operation 
 * </p>
 * 
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@TableName("log_operation")
public class LogOperation implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private String userId;

    @TableField("user_name")
    private String userName;

    @TableField("ip")
    private String ip;

    @TableField("ip_real")
    private String ipReal;

    @TableField("oper_time")
    private Date operTime;

    @TableField("address")
    private String address;

    @TableField("system")
    private String system;

    @TableField("status")
    private String status;

    @TableField("type")
    private String type;

    @TableField("source")
    private String source;

    @TableField("url")
    private String url;

    @TableField("method")
    private String method;

    @TableField("method_type")
    private String methodType;

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



    public LogOperation(Long id,String userId,String userName,String ip,String ipReal,Date operTime,String address,String system,String status,String type,String source,String url,String method,String methodType,String message){
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.ip = ip;
        this.ipReal = ipReal;
        this.operTime = operTime;
        this.address = address;
        this.system = system;
        this.status = status;
        this.type = type;
        this.source = source;
        this.url = url;
        this.method = method;
        this.methodType = methodType;
        this.message = message;
    }

    public LogOperation(){
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