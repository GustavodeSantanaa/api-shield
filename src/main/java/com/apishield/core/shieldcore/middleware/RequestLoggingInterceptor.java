package com.apishield.core.shieldcore.middleware;

import com.apishield.core.shieldcore.service.RateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.apishield.core.shieldcore.domain.RequestLog;
import com.apishield.core.shieldcore.service.RequestLogService;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    @Autowired
    private RequestLogService requestLogService;

    @Autowired
    private RateLimitService rateLimitService;

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

        String apiKey = request.getHeader("x-api-key");

        if (!rateLimitService.isValidApiKey(apiKey)) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write("Invalid API Key");

            return false;
        }

        if (!rateLimitService.isAllowed(apiKey)) {

            response.setStatus(429);

            response.getWriter().write("Rate limit exceeded");

            return false;
        }

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


        RequestLog requestLog = new RequestLog();

        requestLog.setMethod(request.getMethod());
        requestLog.setEndpoint(request.getRequestURI());
        requestLog.setIp(request.getRemoteAddr());

        requestLog.setResponseStatus(response.getStatus());

        requestLog.setExecutionTimeMs(executionTime);

        requestLog.setApiKey(
                request.getHeader("x-api-key")
        );

        requestLogService.saveLog(requestLog);

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
