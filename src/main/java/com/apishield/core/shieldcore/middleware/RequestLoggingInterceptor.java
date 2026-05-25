package com.apishield.core.shieldcore.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        long startTime = System.currentTimeMillis();

        request.setAttribute(START_TIME, startTime);

        String method = request.getMethod();
        String endpoint = request.getRequestURI();
        String ip = request.getRemoteAddr();

        System.out.println(
                "[API SHIELD] Incoming request -> " +
                        method +
                        " " +
                        endpoint +
                        " | IP: " +
                        ip
        );

        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) throws Exception {

        long startTime = (long) request.getAttribute(START_TIME);

        long executionTime = System.currentTimeMillis() - startTime;

        int status = response.getStatus();

        System.out.println(
                "[API SHIELD] Response completed -> " +
                        "Status: " +
                        status +
                        " | Execution Time: " +
                        executionTime +
                        "ms"
        );
    }
}
