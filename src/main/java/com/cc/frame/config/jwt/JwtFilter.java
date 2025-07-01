package com.cc.frame.config.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.stream.Collectors;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	public JwtFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		String uri = request.getRequestURI();
		// 白名单路径
		if (uri.startsWith("/api-admin/sys-user/login") ||
				uri.startsWith("/api-admin/sys-user/register") ||
			uri.startsWith("/swagger-ui") ||
			uri.startsWith("/v3/api-docs") ||
			uri.startsWith("/docs") ||
			uri.startsWith("/swagger-resources") ||
			uri.startsWith("/webjars") ||
			uri.startsWith("/api-docs") ||
			uri.startsWith("/api/") ||
			uri.startsWith("/ui/")) {
			filterChain.doFilter(request, response);
			return;
		}

		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userName;

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		jwt = authHeader.substring(7);
		userName = jwtUtil.extractClaim(jwt, Claims::getSubject);

		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			if (jwtUtil.isTokenValid(jwt, userName)) {
				// 解析权限
				List<String> authorities = jwtUtil.extractClaim(jwt, claims -> {
					Object authObj = claims.get("authorities");
					if (authObj instanceof List<?> list) {
						return list.stream().map(Object::toString).collect(Collectors.toList());
					} else if (authObj instanceof String str) {
						return List.of(str.split(","));
					}
					return List.of();
				});
				var grantedAuthorities = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					userName,
					null,
					grantedAuthorities
				);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}