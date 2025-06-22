package com.itau.token.validator.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;
import java.util.UUID;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional.ofNullable(request.getHeader("x-transaction-id"))
                .ifPresentOrElse(xti -> MDC.put("x-transaction-id",xti),
                        ()-> MDC.put("x-transaction-id", UUID.randomUUID().toString()));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
