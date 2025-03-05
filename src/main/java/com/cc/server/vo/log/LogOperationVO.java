package com.cc.server.vo.log;

import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * log_operation
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */

@Schema(name = "LogOperationVO对象", description = "")
public class LogOperationVO implements Serializable {

	private static final long serialVersionUID = 1L;


	@Schema(description = "主键")
	private Long id;

	@Schema(description = "用户 id")
	private String userId;

	@Schema(description = "用户名")
	private String userName;

	@Schema(description = "登录 IP")
	private String ip;

	@Schema(description = "真实IP")
	private String ipReal;

	@Schema(description = "操作时间")
	private Date operTime;

	@Schema(description = "地址")
	private String address;

	@Schema(description = "设备信息")
	private String system;

	@Schema(description = "登录状态（0 失败，1 成功）")
	private String status;

	@Schema(description = "操作（0 查询，1 新增，2 修改，3 删除）")
	private String type;

	@Schema(description = "来源（0 其他，1 PC，2 手机）")
	private String source;

	@Schema(description = "请求 URL")
	private String url;

	@Schema(description = "请求方法")
	private String method;

	@Schema(description = "请求方式：post，get......")
	private String methodType;

	@Schema(description = "错误消息")
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
		return "LogOperationVO{" +
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