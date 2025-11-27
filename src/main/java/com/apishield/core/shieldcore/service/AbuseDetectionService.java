package com.apishield.core.shieldcore.service;

public interface AbuseDetectionService {
    boolean isSuspicious(String apiKey, String ip);
}
