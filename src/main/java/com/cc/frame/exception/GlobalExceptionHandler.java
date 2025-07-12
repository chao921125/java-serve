package com.cc.frame.exception;

import com.cc.frame.core.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<String> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        logger.warn("参数异常: {} | URI: {}", ex.getMessage(), request.getRequestURI());
        return ApiResponse.error(400, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        logger.warn("参数校验失败: {} | URI: {}", ex.getMessage(), request.getRequestURI());
        return ApiResponse.error(400, "参数校验失败");
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleException(Exception ex, HttpServletRequest request) {
        logger.error("系统异常: {} | URI: {}", ex.getMessage(), request.getRequestURI(), ex);
        return ApiResponse.error(500, "系统异常，请联系管理员");
    }
}
