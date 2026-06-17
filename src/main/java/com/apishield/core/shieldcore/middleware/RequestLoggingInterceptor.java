package com.apishield.core.shieldcore.middleware;

import com.apishield.core.shieldcore.dto.ApiErrorResponse;
import com.apishield.core.shieldcore.service.RateLimitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.apishield.core.shieldcore.domain.RequestLog;
import com.apishield.core.shieldcore.service.RequestLogService;

import java.time.LocalDateTime;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    @Autowired
    private RequestLogService requestLogService;

    @Autowired
    private RateLimitService rateLimitService;

    private static final String START_TIME = "startTime";

    private final ObjectMapper objectMapper =
            new ObjectMapper();


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

            writeErrorResponse(
                    response,
                    401,
                    "Unauthorized",
                    "Invalid API Key"
            );

            return false;
        }

        if (!rateLimitService.isAllowed(apiKey)) {

            writeErrorResponse(
                    response,
                    429,
                    "Too Many Requests",
                    "Rate limit exceeded"
            );

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

    private void writeErrorResponse(
            HttpServletResponse response,
            int status,
            String error,
            String message
    ) throws Exception {
        ApiErrorResponse errorResponse =
                new ApiErrorResponse(
                        LocalDateTime.now().toString(),
                        status,
                        error,
                        message
                );

        String jsonResponse =
                objectMapper.writeValueAsString(errorResponse);

        response.setStatus(status);

        response.setContentType("application/json");

        response.getWriter().write(jsonResponse);
    }

}
