package com.cc.frame.config.security;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalAuthentication
public class SecurityConfig {

    private final ApplicationContext applicationContext;

    public SecurityConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 密码加密使用的编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated()) // 其他请求都需要认证
//                .sessionManagement(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .logout(AbstractHttpConfigurer::disable)
//                .requestCache(requestCache -> requestCache.requestCache(new NullRequestCache())) // 前后端分离项目，无需重定向，不需要session
//                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationExceptionHandler))
//                .anonymous(AbstractHttpConfigurer::disable); // 无需给匿名用户身份
//
//        // 处理 SpringSecurity 异常响应结果。响应数据的结构，改成业务统一的JSON结构。不要框架默认的响应结构
//        http.exceptionHandling(exceptionHandling ->
//                exceptionHandling
//                        // 认证失败异常
//                        .authenticationEntryPoint(authenticationExceptionHandler)
//                        // 鉴权失败异常
//                        .accessDeniedHandler(authorizationExceptionHandler)
//        );
//        // 其他未知异常. 尽量提前加载。
//        http.addFilterBefore(globalSpringSecurityExceptionHandler, SecurityContextHolderFilter.class);
//
//        http.securityMatcher("/open-api/business-3") // 使用securityMatcher限定当前配置作用的路径
//                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
//        return http.build();
//    }
}
