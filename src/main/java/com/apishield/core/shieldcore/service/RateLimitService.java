package com.apishield.core.shieldcore.service;

public interface RateLimitService {
    boolean isAllowed(String apiKey);
}
