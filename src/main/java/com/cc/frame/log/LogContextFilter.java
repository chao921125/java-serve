package com.cc.frame.log;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Component
public class LogContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // no-op
    }

    @Override
    public void destroy() {
        // no-op
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String traceId = UUID.randomUUID().toString().replaceAll("-", "");
            LogContext.setTraceId(traceId);
            MDC.put("traceId", traceId);
            // 可根据登录态设置 userId
            String userId = null;
            if (request instanceof HttpServletRequest) {
                Object uid = ((HttpServletRequest) request).getSession().getAttribute("userId");
                if (uid != null) {
                    userId = uid.toString();
                    LogContext.setUserId(userId);
                    MDC.put("userId", userId);
                }
            }
            chain.doFilter(request, response);
        } finally {
            LogContext.clearAll();
            MDC.remove("traceId");
            MDC.remove("userId");
        }
    }
}
