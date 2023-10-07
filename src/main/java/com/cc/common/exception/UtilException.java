package com.cc.common.exception;

import java.io.Serial;

/**
 * 工具类异常
 *
 * @author cc
 */
public class UtilException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UtilException(Throwable e) {
        super(e.getMessage(), e);
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
