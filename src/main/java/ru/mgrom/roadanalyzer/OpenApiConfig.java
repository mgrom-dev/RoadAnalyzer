package ru.mgrom.roadanalyzer;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

public class OpenApiConfig {
     @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Roadanalyzer API")
                        .version("1.0")
                        .description("API for managing endpoints and projects"));
    }
}
