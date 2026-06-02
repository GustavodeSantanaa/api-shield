package com.apishield.core.shieldcore.service.impl;

import com.apishield.core.shieldcore.service.RateLimitService;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitServiceImpl implements RateLimitService {

    private static final String VALID_API_KEY = "api-shield-secret-key";

    private static final int MAX_REQUESTS = 5;

    private final Map<String, Integer> requestCounts =
            new ConcurrentHashMap<>();

    @Override
    public boolean isAllowed(String apiKey) {

        int currentCount = requestCounts.getOrDefault(apiKey, 0);

        if (currentCount >= MAX_REQUESTS) {
            return false;
        }

        requestCounts.put(apiKey, currentCount + 1);

        return true;

    }

    @Override
    public boolean isValidApiKey(String apiKey) {

        return VALID_API_KEY.equals(apiKey);

    }
}
