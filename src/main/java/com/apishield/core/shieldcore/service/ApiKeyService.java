package com.apishield.core.shieldcore.service;

import com.apishield.core.shieldcore.domain.ApiKey;

import java.util.List;
import java.util.Optional;

public interface ApiKeyService {

    ApiKey save(ApiKey apiKey);

    List<ApiKey> findAll();

    Optional<ApiKey> findById(Long id);

    Optional<ApiKey> findByApiKey(String apiKey);

    void delete(Long id);
}
