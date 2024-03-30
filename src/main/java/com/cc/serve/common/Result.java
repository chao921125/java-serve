package com.cc.serve.common;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public Result resultSuccess() {
        Result result = new Result();
        result.code = EnumResult.SUCCESS.getCode();
        result.msg = EnumResult.SUCCESS.getMsg();
        return result;
    }

    public Result<Object> resultSuccess(Object data) {
        Result<Object> result = new Result<Object>();
        result.code = EnumResult.SUCCESS.getCode();
        result.msg = EnumResult.SUCCESS.getMsg();
        result.setData(data);
        return result;
    }

    public Result resultFail(EnumResult enumResult) {
        Result result = new Result();
        result.code = enumResult.getCode();
        result.msg = enumResult.getMsg();
        return result;
    }

    public Result resultFail(EnumResult enumResult, Object data) {
        Result<Object> result = new Result<Object>();
        result.code = enumResult.getCode();
        result.msg = enumResult.getMsg();
        result.setData(data);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
