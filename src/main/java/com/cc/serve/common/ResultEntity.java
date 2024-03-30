package com.cc.serve.common;

import java.io.Serializable;

public class ResultEntity<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public ResultEntity resultSuccess() {
        ResultEntity result = new ResultEntity();
        result.code = EnumResult.SUCCESS.getCode();
        result.msg = EnumResult.SUCCESS.getMsg();
        return result;
    }

    public ResultEntity<Object> resultSuccess(Object data) {
        ResultEntity<Object> result = new ResultEntity<Object>();
        result.code = EnumResult.SUCCESS.getCode();
        result.msg = EnumResult.SUCCESS.getMsg();
        result.setData(data);
        return result;
    }

    public ResultEntity resultFail(EnumResult enumResult) {
        ResultEntity result = new ResultEntity();
        result.code = enumResult.getCode();
        result.msg = enumResult.getMsg();
        return result;
    }

    public ResultEntity resultFail(EnumResult enumResult, Object data) {
        ResultEntity<Object> result = new ResultEntity<Object>();
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
