package ru.mgrom.roadanalyzer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableTransactionManagement
public class AppConfig {

    public static final String SESSION_INFO_URL = "http://localhost:8080/session-info";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}