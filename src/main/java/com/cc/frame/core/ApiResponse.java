package com.cc.frame.core;

public class ApiResponse<T> {
    private int code;
    private String msg;
    private T data;

    public ApiResponse() {}
    public ApiResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(0, "操作成功", data);
    }
    public static <T> ApiResponse<T> success(String msg, T data) {
        return new ApiResponse<>(0, msg, data);
    }
    public static <T> ApiResponse<T> error(int code, String msg) {
        return new ApiResponse<>(code, msg, null);
    }
    public static <T> ApiResponse<T> error(int code, String msg, T data) {
        return new ApiResponse<>(code, msg, data);
    }
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
} 