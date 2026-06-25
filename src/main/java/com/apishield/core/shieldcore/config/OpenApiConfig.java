package com.apishield.core.shieldcore.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Shield",
                version = "1.0",
                description = "Sistema de proteção e monitoramento de APIs desenvolvido com Spring Boot",
                contact = @Contact(
                        name = "Gustavo Lima"
                )
        )
)
public class OpenApiConfig {


}
