package ru.mgrom.roadanalyzer;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**").permitAll() // Пример открытого доступа
                        .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                )
                .formLogin(form -> form
                        .loginPage("/login") // Укажите страницу логина
                        .permitAll() // Разрешить доступ к странице логина всем пользователям
                )
                .logout(logout -> logout
                        .permitAll() // Разрешить выход для всех пользователей
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // Максимальное количество сессий для одного пользователя
                        .maxSessionsPreventsLogin(true) // Запретить новую сессию, если максимальное количество
                                                        // достигнуто
                );

        return http.build();
    }
}