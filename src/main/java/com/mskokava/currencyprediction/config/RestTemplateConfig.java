package com.mskokava.currencyprediction.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(
            LoggingInterceptor loggingInterceptor,
            RestTemplateBuilder restTemplateBuilder) {
        final var factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        return restTemplateBuilder.requestFactory(() -> factory)
                .interceptors(List.of(loggingInterceptor))
                .build();
    }
}
