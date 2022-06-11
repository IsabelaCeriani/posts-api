package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConfig {

    @Bean
    public WebClient followApi() {
        return WebClient.create("http://localhost:8087");
    }
}
