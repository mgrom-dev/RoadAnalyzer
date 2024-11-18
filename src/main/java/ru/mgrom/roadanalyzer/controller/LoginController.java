package ru.mgrom.roadanalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.service.UserService;
import ru.mgrom.roadanalyzer.service.UserService.AuthStatus;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestParam String login, @RequestParam String pswd,
            @RequestParam(required = false) String email, HttpServletRequest request) {

        if (!email.isBlank()) {
            // registration
            
            System.out.println("Registration: Username: " + login + ", Password: " + pswd + ", Email: " + email);
            return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно зарегистрирован.");
        } else {
            AuthStatus status = userService.authorize(login, pswd, request);

            return switch (status) {
                case SUCCESS -> ResponseEntity.status(HttpStatus.FOUND).header("Location", "/").build();
                case PASSWORD_INCORRECT -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Неверный логин или пароль.\"}");
                case USER_NOT_FOUND -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Пользователь не найден.\"}");
                default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"error\": \"Произошла ошибка. Попробуйте позже.\"}");
            };
        }
    }
}
