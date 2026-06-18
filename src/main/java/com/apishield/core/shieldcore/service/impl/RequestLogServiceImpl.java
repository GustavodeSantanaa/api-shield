package com.apishield.core.shieldcore.service.impl;

import com.apishield.core.shieldcore.domain.RequestLog;
import com.apishield.core.shieldcore.dto.EndpointStatsResponse;
import com.apishield.core.shieldcore.repository.RequestLogRepository;
import com.apishield.core.shieldcore.service.RequestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<RequestLog> findByResponseStatus(Integer status) {
        return requestLogRepository.findByResponseStatus(status);
    }

    @Override
    public Long countLogs() {
        return requestLogRepository.count();
    }

    @Override
    public Long countByResponseStatus(Integer status) {

        return requestLogRepository
                .countByResponseStatus(status);

    }

    @Override
    public List<Object[]> countRequestsByEndpoint() {

        return requestLogRepository
                .countRequestsByEndpoint();

    }

    @Override
    public List getEndpointStats() {

        List<Object[]> results =
                requestLogRepository.countRequestsByEndpoint();

        List<EndpointStatsResponse> stats =
                new ArrayList<>();

        for (Object[] result : results) {

            String endpoint =
                    (String) result[0];

            Long requestCount =
                    (Long) result[1];

            stats.add(
                    new EndpointStatsResponse(
                            endpoint,
                            requestCount
                    )
            );
        }

        return stats;

    }
}
