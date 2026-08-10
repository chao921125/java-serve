package com.cc.framework.base;

import com.cc.core.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一响应对象
 *
 * @param <T> 数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 状态码 */
    private int code;

    /** 消息 */
    private String msg;

    /** 数据 */
    private T data;

    /** 时间戳 */
    private long timestamp;

    // ---- 成功 ----

    public static <T> R<T> ok() {
        return ok(null);
    }

    public static <T> R<T> ok(T data) {
        return of(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static <T> R<T> ok(String msg, T data) {
        return of(ResultCode.SUCCESS.getCode(), msg, data);
    }

    // ---- 失败 ----

    public static <T> R<T> fail() {
        return fail(ResultCode.ERROR.getMsg());
    }

    public static <T> R<T> fail(String msg) {
        return of(ResultCode.ERROR.getCode(), msg, null);
    }

    public static <T> R<T> fail(int code, String msg) {
        return of(code, msg, null);
    }

    public static <T> R<T> fail(ResultCode resultCode) {
        return of(resultCode.getCode(), resultCode.getMsg(), null);
    }

    // ---- 构建 ----

    public static <T> R<T> of(int code, String msg, T data) {
        R<T> r = new R<>();
        r.code = code;
        r.msg = msg;
        r.data = data;
        r.timestamp = System.currentTimeMillis();
        return r;
    }

    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return this.code == ResultCode.SUCCESS.getCode();
    }
}
