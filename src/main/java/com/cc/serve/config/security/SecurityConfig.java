package com.cc.serve.config.security;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .logout(logout -> logout.disable())
                .sessionManagement(sessionManagement -> sessionManagement.disable())
                .requestCache(requestCache -> requestCache.requestCache(new NullRequestCache())) // 前后端分离项目，无需重定向，不需要session
                .anonymous(anonymous -> anonymous.disable()); // 无需给匿名用户身份

        // 处理 SpringSecurity 异常响应结果。响应数据的结构，改成业务统一的JSON结构。不要框架默认的响应结构
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling
                        // 认证失败异常
                        .authenticationEntryPoint(authenticationExceptionHandler)
                        // 鉴权失败异常
                        .accessDeniedHandler(authorizationExceptionHandler)
        );
        // 其他未知异常. 尽量提前加载。
        http.addFilterBefore(globalSpringSecurityExceptionHandler, SecurityContextHolderFilter.class);

        // 使用securityMatcher限定当前配置作用的路径
        http.securityMatcher("/user/login/*")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        LoginSuccessHandler loginSuccessHandler = applicationContext.getBean(LoginSuccessHandler.class);
        LoginFailHandler loginFailHandler = applicationContext.getBean(LoginFailHandler.class);

        // 加一个登录方式。用户名、密码登录
        UsernameAuthenticationFilter usernameLoginFilter = new UsernameAuthenticationFilter(
                new AntPathRequestMatcher("/user/login/username", HttpMethod.POST.name()),
                new ProviderManager(
                        List.of(applicationContext.getBean(UsernameAuthenticationProvider.class))),
                loginSuccessHandler,
                loginFailHandler);
        http.addFilterBefore(usernameLoginFilter, UsernamePasswordAuthenticationFilter.class);

        // 加一个登录方式。短信验证码 登录
        SmsAuthenticationFilter smsLoginFilter = new SmsAuthenticationFilter(
                new AntPathRequestMatcher("/user/login/sms", HttpMethod.POST.name()),
                new ProviderManager(
                        List.of(applicationContext.getBean(SmsAuthenticationProvider.class))),
                loginSuccessHandler,
                loginFailHandler);
        http.addFilterBefore(smsLoginFilter, UsernamePasswordAuthenticationFilter.class);


        // 加一个登录方式。Gitee 登录
        GiteeAuthenticationFilter giteeFilter = new GiteeAuthenticationFilter(
                new AntPathRequestMatcher("/user/login/gitee", HttpMethod.POST.name()),
                new ProviderManager(
                        List.of(applicationContext.getBean(GiteeAuthenticationProvider.class))),
                loginSuccessHandler,
                loginFailHandler);
        http.addFilterBefore(giteeFilter, UsernamePasswordAuthenticationFilter.class);

        // 使用securityMatcher限定当前配置作用的路径
        http.securityMatcher("/open-api/business-1")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        MyJwtAuthenticationFilter openApi1Filter = new MyJwtAuthenticationFilter(
                applicationContext.getBean(JwtService.class));
        // 加一个登录方式。用户名、密码登录
        http.addFilterBefore(openApi1Filter, UsernamePasswordAuthenticationFilter.class);

        http.securityMatcher("/open-api/business-3") // 使用securityMatcher限定当前配置作用的路径
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        return http.build();
    }
}
