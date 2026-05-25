package com.apishield.core.shieldcore.service.impl;

import com.apishield.core.shieldcore.service.RateLimitService;
import org.springframework.stereotype.Service;

@Service
public class RateLimitServiceImpl implements RateLimitService {

    private static final String VALID_API_KEY = "api-shield-secret-key";

    @Override
    public boolean isAllowed(String apiKey) {

        return true;

    }

    @Override
    public boolean isValidApiKey(String apiKey) {

        return VALID_API_KEY.equals(apiKey);

    }
}
