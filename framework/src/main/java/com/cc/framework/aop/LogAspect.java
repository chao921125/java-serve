package com.cc.framework.aop;

import com.alibaba.fastjson2.JSON;
import com.cc.core.entity.LogOperation;
import com.cc.core.mapper.LogOperationMapper;
import com.cc.framework.annotation.Log;
import com.cc.framework.config.security.SecurityUtil;
import com.cc.framework.utils.IpUtil;
import com.cc.framework.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 操作日志 AOP 切面
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final LogOperationMapper logOperationMapper;

    @Around("@annotation(com.cc.framework.annotation.Log)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        LogOperation logOperation = new LogOperation();
        HttpServletRequest request = ServletUtil.getRequest();

        try {
            // 获取注解信息
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Log logAnnotation = signature.getMethod().getAnnotation(Log.class);

            // 设置基础信息
            logOperation.setTitle(logAnnotation.title());
            logOperation.setBusinessType(logAnnotation.businessType().getCode());
            logOperation.setOperTime(LocalDateTime.now());

            // 设置用户信息
            try {
                logOperation.setUserId(SecurityUtil.getUserId());
                logOperation.setUserName(SecurityUtil.getUsername());
            } catch (Exception e) {
                logOperation.setUserId(0L);
                logOperation.setUserName("system");
            }

            // 设置请求信息
            if (request != null) {
                logOperation.setIp(IpUtil.getClientIp(request));
                logOperation.setUrl(request.getRequestURI());
                logOperation.setMethod(request.getMethod());
                logOperation.setMethodType(request.getMethod());
            }

            // 保存请求参数
            if (logAnnotation.isSaveRequestData()) {
                Object[] args = joinPoint.getArgs();
                if (args != null && args.length > 0) {
                    try {
                        logOperation.setParams(JSON.toJSONString(args));
                    } catch (Exception e) {
                        logOperation.setParams("参数序列化失败");
                    }
                }
            }

            // 执行目标方法
            Object result = joinPoint.proceed();

            // 设置成功状态
            logOperation.setStatus(0);
            logOperation.setMessage("操作成功");

            // 保存响应数据
            if (logAnnotation.isSaveResponseData() && result != null) {
                try {
                    logOperation.setResult(JSON.toJSONString(result));
                } catch (Exception e) {
                    logOperation.setResult("响应序列化失败");
                }
            }

            return result;
        } catch (Throwable e) {
            // 设置失败状态
            logOperation.setStatus(1);
            logOperation.setMessage("操作失败: " + e.getMessage());
            logOperation.setExceptionMsg(getExceptionDetail(e));
            throw e;
        } finally {
            logOperation.setCostTime(System.currentTimeMillis() - startTime);
            // 异步保存日志（不阻塞主流程）
            try {
                logOperationMapper.insert(logOperation);
            } catch (Exception e) {
                log.error("保存操作日志失败", e);
            }
        }
    }

    /**
     * 获取异常详情
     */
    private String getExceptionDetail(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getClass().getName()).append(": ").append(e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            if (element.getClassName().startsWith("com.cc")) {
                sb.append("\n\tat ").append(element);
            }
        }
        if (sb.length() > 2000) {
            sb.setLength(2000);
        }
        return sb.toString();
    }
}
