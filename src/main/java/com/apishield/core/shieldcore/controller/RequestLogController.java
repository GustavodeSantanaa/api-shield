package com.apishield.core.shieldcore.controller;

import com.apishield.core.shieldcore.domain.RequestLog;
import com.apishield.core.shieldcore.dto.LogStatsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.apishield.core.shieldcore.service.RequestLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/logs")
public class RequestLogController {

    @Autowired
    private RequestLogService requestLogService;

    @GetMapping
    public List<RequestLog> getLogs() {

        return requestLogService.findAllLogs();

    }

    @GetMapping("/status/{status}")
    public List<RequestLog> getLogsByStatus(
            @PathVariable Integer status
    ) {

        return requestLogService.findByResponseStatus(status);

    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestLog> getLogById(@PathVariable Long id) {

        Optional<RequestLog> log =
                requestLogService.findById(id);

        if(log.isPresent()) {
            return ResponseEntity.ok().body(log.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/stats")
    public LogStatsResponse getStats() {

        Long totalRequests =
                requestLogService.countLogs();

        Long successfulRequests =
                requestLogService.countByResponseStatus(200);

        Long blockedRequests =
                requestLogService.countByResponseStatus(429);

        return new LogStatsResponse(
                totalRequests,
                successfulRequests,
                blockedRequests
        );
    }
}
