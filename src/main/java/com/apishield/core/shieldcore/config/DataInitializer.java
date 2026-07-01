package com.apishield.core.shieldcore.config;

import com.apishield.core.shieldcore.domain.ApiKey;
import com.apishield.core.shieldcore.repository.ApiKeyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ApiKeyRepository apiKeyRepository;

    public DataInitializer(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public void run(String... args) {

        if (apiKeyRepository.count() == 0) {

            ApiKey apiKey = new ApiKey();

            apiKey.setApiKey("API-SHIELD-DEV-123456");

            apiKey.setOwner("Administrador");

            apiKey.setActive(true);

            apiKey.setCreatedAt(LocalDateTime.now());

            apiKey.setLastUsed(null);

            apiKeyRepository.save(apiKey);

            System.out.println("----------------------------------------");
            System.out.println("API Key padrão criada com sucesso!");
            System.out.println("Chave: API-SHIELD-DEV-123456");
            System.out.println("----------------------------------------");
        }
    }
}
