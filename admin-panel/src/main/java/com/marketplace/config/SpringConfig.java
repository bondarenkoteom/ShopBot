package com.marketplace.config;

import com.marketplace.client.HttpCoreClient;
import com.marketplace.client.HttpCoreInterface;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class SpringConfig {

    @Bean
    public HttpCoreInterface httpCoreInterface(HttpCoreClient httpCoreClient) {
        return httpCoreClient.getHttpInterface();
    }
}