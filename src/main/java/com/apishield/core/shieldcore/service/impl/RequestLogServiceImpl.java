package com.apishield.core.shieldcore.service.impl;

import com.apishield.core.shieldcore.domain.RequestLog;
import com.apishield.core.shieldcore.repository.RequestLogRepository;
import com.apishield.core.shieldcore.service.RequestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestLogServiceImpl implements RequestLogService {

    @Autowired
    private RequestLogRepository requestLogRepository;

    @Override
    public RequestLog saveLog(RequestLog log) {

        return requestLogRepository.save(log);

    }
}
