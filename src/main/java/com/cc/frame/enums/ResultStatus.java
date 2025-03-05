package com.cc.frame.enums;

public enum ResultStatus {
	SUCCESS(0, ""),
	VALIDATED_ERROR(9, ""),
	UNKNOWN_ERROR(-1, ""),
	SERVER_ERROR(500, "");

	private Integer code;
	private String msg;

	ResultStatus(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
