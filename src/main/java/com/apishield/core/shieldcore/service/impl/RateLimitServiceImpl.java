package com.apishield.core.shieldcore.service.impl;

import com.apishield.core.shieldcore.domain.RateLimitInfo;
import com.apishield.core.shieldcore.service.RateLimitService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitServiceImpl implements RateLimitService {

    private static final String VALID_API_KEY = "api-shield-secret-key";

    private static final long WINDOW_DURATION_MS = 60_000;

    private static final int MAX_REQUESTS = 5;

    private final Map<String, RateLimitInfo> requestCounts =
            new ConcurrentHashMap<>();

    @Override
    public boolean isAllowed(String apiKey) {

        if(!requestCounts.containsKey(apiKey)){
            RateLimitInfo info =
                    new RateLimitInfo(
                            1,
                            System.currentTimeMillis()
                    );

            requestCounts.put(apiKey, info);

            return true;
        }

        RateLimitInfo info = requestCounts.get(apiKey);

        long currentTime = System.currentTimeMillis();

        long elapsedTime = currentTime - info.getWindowStartTime();

        if (elapsedTime > WINDOW_DURATION_MS){
            info.setRequestCount(1);

            info.setWindowStartTime(currentTime);

            return true;
        }

        if (info.getRequestCount() >= MAX_REQUESTS) {
            return false;
        }

        info.incrementRequestCount();

        return true;
    }

    @Override
    public boolean isValidApiKey(String apiKey) {

        return VALID_API_KEY.equals(apiKey);

    }
}
