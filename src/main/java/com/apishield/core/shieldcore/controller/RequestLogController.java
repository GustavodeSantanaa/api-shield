package com.apishield.core.shieldcore.controller;

import com.apishield.core.shieldcore.domain.RequestLog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.apishield.core.shieldcore.service.RequestLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/logs")
public class RequestLogController {

    @Autowired
    private RequestLogService requestLogService;

    @GetMapping()
    public List<RequestLog> getLogs() {
        return requestLogService.findAllLogs();
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
}
