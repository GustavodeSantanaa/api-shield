package com.apishield.core.shieldcore.service;

import com.apishield.core.shieldcore.domain.RequestLog;

import java.util.List;
import java.util.Optional;

public interface RequestLogService {

    RequestLog saveLog(RequestLog log);

    List<RequestLog> findAllLogs();

    Optional<RequestLog> findById(Long log);

    List<RequestLog> findByResponseStatus(Integer status);

    Long countLogs();

    Long countByResponseStatus(Integer status);
}
