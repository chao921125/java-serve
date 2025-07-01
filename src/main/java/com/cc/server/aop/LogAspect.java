package com.cc.server.aop;

import com.cc.server.entity.log.LogLogin;
import com.cc.server.entity.log.LogOperation;
import com.cc.server.service.log.LogLoginService;
import com.cc.server.service.log.LogOperationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Date;

@Aspect
@Component
public class LogAspect {
    @Resource
    private LogLoginService logLoginService;
    @Resource
    private LogOperationService logOperationService;

    // 登录接口切点
    @Pointcut("execution(* com.cc.server.controller.system.SysUserController.login(..))")
    public void loginPointcut() {}

    // 业务操作切点（可根据需要调整包路径）
    @Pointcut("(@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping) || @annotation(org.springframework.web.bind.annotation.DeleteMapping)) && !execution(* com.cc.server.controller.system.SysUserController.login(..))")
    public void operationPointcut() {}

    // 登录成功后记录登录日志
    @AfterReturning(pointcut = "loginPointcut()", returning = "result")
    public void afterLogin(JoinPoint joinPoint, Object result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        Object[] args = joinPoint.getArgs();
        String loginName = "";
        if (args.length > 0 && args[0] instanceof com.cc.server.vo.system.SysUserVO) {
            com.cc.server.vo.system.SysUserVO loginVO = (com.cc.server.vo.system.SysUserVO) args[0];
            loginName = loginVO.getUserName() != null ? loginVO.getUserName() : "";
        }
        LogLogin log = new LogLogin();
        log.setUserName(loginName);
        log.setIp(ip);
        log.setLoginTime(new Date());
        log.setStatus("1"); // 1=成功
        log.setMessage("登录成功");
        logLoginService.insertLogLogin(log);
    }

    // 登录异常记录失败日志
    @AfterThrowing(pointcut = "loginPointcut()", throwing = "ex")
    public void afterLoginException(JoinPoint joinPoint, Throwable ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        Object[] args = joinPoint.getArgs();
        String loginName = "";
        if (args.length > 0 && args[0] instanceof com.cc.server.vo.system.SysUserVO) {
            com.cc.server.vo.system.SysUserVO loginVO = (com.cc.server.vo.system.SysUserVO) args[0];
            loginName = loginVO.getUserName() != null ? loginVO.getUserName() : "";
        }
        LogLogin log = new LogLogin();
        log.setUserName(loginName);
        log.setIp(ip);
        log.setLoginTime(new Date());
        log.setStatus("0"); // 0=失败
        log.setMessage("登录失败: " + ex.getMessage());
        logLoginService.insertLogLogin(log);
    }

    // 操作日志（增删改）
    @AfterReturning(pointcut = "operationPointcut()", returning = "result")
    public void afterOperation(JoinPoint joinPoint, Object result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        String url = request.getRequestURI();
        String method = request.getMethod();
        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            params = java.util.Arrays.toString(joinPoint.getArgs());
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        LogOperation log = new LogOperation();
        log.setUserName(getCurrentUserName());
        log.setIp(ip);
        log.setOperTime(new Date());
        log.setUrl(url);
        log.setMethod(methodName);
        log.setMethodType(method);
        log.setParams(params);
        log.setStatus("1");
        log.setMessage("操作成功");
        logOperationService.insertLogOperation(log);
    }

    @AfterThrowing(pointcut = "operationPointcut()", throwing = "ex")
    public void afterOperationException(JoinPoint joinPoint, Throwable ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        String url = request.getRequestURI();
        String method = request.getMethod();
        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            params = java.util.Arrays.toString(joinPoint.getArgs());
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        LogOperation log = new LogOperation();
        log.setUserName(getCurrentUserName());
        log.setIp(ip);
        log.setOperTime(new Date());
        log.setUrl(url);
        log.setMethod(methodName);
        log.setMethodType(method);
        log.setParams(params);
        log.setStatus("0");
        log.setMessage("操作异常: " + ex.getMessage());
        logOperationService.insertLogOperation(log);
    }

    // 获取当前登录用户名（可根据实际安全框架调整）
    private String getCurrentUserName() {
        // TODO: 可集成Spring Security等获取当前用户
        return "未知用户";
    }
} 