package ru.mgrom.roadanalyzer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        authorize -> authorize
                                .anyRequest().permitAll())
                .sessionManagement(session -> session
                        .maximumSessions(1) // Максимальное количество сессий для одного
                                            // пользователя
                        .maxSessionsPreventsLogin(true) // Запретить новую сессию, если
                // максимальное количество
                // достигнуто
                );

        return http.build();
    }

    // ignoring filter chain
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().anyRequest();
    }
}
