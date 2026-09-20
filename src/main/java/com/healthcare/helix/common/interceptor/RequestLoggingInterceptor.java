package com.healthcare.helix.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {
    /*
     * NOTE - interceptors has main 3 methods
     * 1 - public boolean preHandle() --> Before controller
     * 2 - public void postHandle() --> After controller, before response completes - used in Modify/model-related processing
     * 3 - public void afterCompletion() --> After request is completely finished - used in After request is completely finished
     * */

    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // Request Tracking
        String requestId = UUID.randomUUID().toString();
        request.setAttribute("requestId", requestId);

        // Logging
        request.setAttribute(START_TIME, System.currentTimeMillis());
        log.info(">>> Request: {} {} " + request.getMethod(), request.getRequestURI());
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME);
        long executionTime = System.currentTimeMillis() - startTime;
        log.info(">>> Response: {} {} | status={} | time={}ms", request.getMethod(), request.getRequestURI(), response.getStatus(), executionTime);


        String requestId = (String) request.getAttribute("requestId");
        log.info(">>> Request completed: requestId={}, status={}", requestId, response.getStatus());

    }

}
