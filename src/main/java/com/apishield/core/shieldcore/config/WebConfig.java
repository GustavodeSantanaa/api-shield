package com.apishield.core.shieldcore.config;

import com.apishield.core.shieldcore.middleware.RequestLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RequestLoggingInterceptor requestLoggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(requestLoggingInterceptor)
                .excludePathPatterns(
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                );
    }

}
