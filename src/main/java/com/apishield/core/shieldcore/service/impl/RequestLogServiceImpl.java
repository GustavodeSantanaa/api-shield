package com.apishield.core.shieldcore.service.impl;

import com.apishield.core.shieldcore.domain.RequestLog;
import com.apishield.core.shieldcore.repository.RequestLogRepository;
import com.apishield.core.shieldcore.service.RequestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestLogServiceImpl implements RequestLogService {

    @Autowired
    private RequestLogRepository requestLogRepository;

    @Override
    public RequestLog saveLog(RequestLog log) {

        return requestLogRepository.save(log);

    }

    @Override
    public List<RequestLog> findAllLogs() {

        return requestLogRepository.findAll();
    }

    @Override
    public Optional<RequestLog> findById(Long log) {

        return requestLogRepository.findById(log);
    }
}
