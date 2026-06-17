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

            ApiErrorResponse errorResponse =
                    new ApiErrorResponse(
                            LocalDateTime.now().toString(),
                            401,
                            "Unauthorized",
                            "Invalid API Key"
                    );

            String jsonResponse =
                    objectMapper.writeValueAsString(errorResponse);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.setContentType("application/json");

            response.getWriter().write(jsonResponse);

            return false;
        }

        if (!rateLimitService.isAllowed(apiKey)) {

            ApiErrorResponse errorResponse =
                    new ApiErrorResponse(
                            LocalDateTime.now().toString(),
                            429,
                            "Too Many Requests",
                            "Rate limit exceeded"
                    );

            String jsonResponse =
                    objectMapper.writeValueAsString(errorResponse);

            response.setStatus(429);

            response.setContentType("application/json");

            response.getWriter().write(jsonResponse);

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
