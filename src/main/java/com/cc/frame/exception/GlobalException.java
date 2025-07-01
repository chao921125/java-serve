package com.cc.frame.exception;

import com.cc.frame.core.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

@RestControllerAdvice
public class GlobalException {
	@ExceptionHandler(NoHandlerFoundException.class)
	public ApiResponse<Void> handle404(NoHandlerFoundException ex) {
		return ApiResponse.error(404, "路径未找到");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResponse<Void> handle400(MethodArgumentNotValidException ex) {
		return ApiResponse.error(400, "参数校验失败: " + ex.getBindingResult().getFieldError().getDefaultMessage());
	}

	@ExceptionHandler(AuthenticationException.class)
	public ApiResponse<Void> handle401(AuthenticationException ex) {
		return ApiResponse.error(401, "未认证/未登录");
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ApiResponse<Void> handle403(AccessDeniedException ex) {
		return ApiResponse.error(403, "无权限");
	}

	@ExceptionHandler(ServiceException.class)
	public ApiResponse<Void> handleService(ServiceException ex) {
		return ApiResponse.error(ex.getCode(), ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ApiResponse<Void> handle500(Exception ex) {
		return ApiResponse.error(500, "系统异常: " + ex.getMessage());
	}
}
