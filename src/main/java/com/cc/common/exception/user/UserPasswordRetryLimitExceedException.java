package com.cc.common.exception.user;

import java.io.Serial;

/**
 * 用户错误最大次数异常类
 *
 * @author cc
 */
public class UserPasswordRetryLimitExceedException extends UserException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitExceedException(int retryLimitCount, int lockTime) {
        super("user.password.retry.limit.exceed", new Object[]{retryLimitCount, lockTime});
    }
}
