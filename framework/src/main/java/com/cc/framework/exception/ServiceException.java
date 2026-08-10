package com.cc.framework.exception;

import lombok.Getter;

/**
 * 业务异常
 */
@Getter
public class ServiceException extends RuntimeException {

    private final int code;

    public ServiceException(String message) {
        super(message);
        this.code = 500;
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * 快捷创建：参数错误
     */
    public static ServiceException badRequest(String message) {
        return new ServiceException(400, message);
    }

    /**
     * 快捷创建：未找到
     */
    public static ServiceException notFound(String message) {
        return new ServiceException(404, message);
    }

    /**
     * 快捷创建：服务器错误
     */
    public static ServiceException error(String message) {
        return new ServiceException(500, message);
    }
}
