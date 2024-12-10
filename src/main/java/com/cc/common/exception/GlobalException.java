package com.cc.common.exception;

public class GlobalException extends RuntimeException {
    public GlobalException(Throwable e) {
        super(e.getMessage(), e);
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
