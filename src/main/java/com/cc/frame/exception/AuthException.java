package com.cc.frame.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

public class AuthException implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		PrintWriter writer = response.getWriter();
		writer.print(String.format("{\"code\":%d,\"message\":\"%s\",\"timestamp\":%d}",
				HttpStatus.UNAUTHORIZED.value(),
				authException.getMessage() != null ? authException.getMessage() : "Unauthorized access",
				System.currentTimeMillis()));
		writer.flush();
		writer.close();
	}
}
