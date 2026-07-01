package com.apishield.core.shieldcore.service.impl;

import com.apishield.core.shieldcore.domain.ApiKey;
import com.apishield.core.shieldcore.repository.ApiKeyRepository;
import com.apishield.core.shieldcore.service.ApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApiKeyServiceImpl implements ApiKeyService {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Override
    public ApiKey save(ApiKey apiKey) {
        return apiKeyRepository.save(apiKey);
    }

    @Override
    public List<ApiKey> findAll() {
        return apiKeyRepository.findAll();
    }

    @Override
    public Optional<ApiKey> findById(Long id) {
        return apiKeyRepository.findById(id);
    }

    @Override
    public Optional<ApiKey> findByApiKey(String apiKey) {
        return apiKeyRepository.findByApiKey(apiKey);
    }

    @Override
    public void delete(Long id) {
        apiKeyRepository.deleteById(id);
    }
}
