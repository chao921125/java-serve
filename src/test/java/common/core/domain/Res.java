package common.core.domain;

import common.core.constant.Constants;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author ruoyi
 */
public class Res<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = Constants.SUCCESS;

    /**
     * 失败
     */
    public static final int FAIL = Constants.FAIL;

    private int code;

    private String msg;

    private T data;

    public static <T> Res<T> ok() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> Res<T> ok(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> Res<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> Res<T> fail() {
        return restResult(null, FAIL, null);
    }

    public static <T> Res<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> Res<T> fail(T data) {
        return restResult(data, FAIL, null);
    }

    public static <T> Res<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> Res<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> Res<T> restResult(T data, int code, String msg) {
        Res<T> apiResult = new Res<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
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

    public static <T> Boolean isError(Res<T> ret) {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(Res<T> ret) {
        return Res.SUCCESS == ret.getCode();
    }
}
