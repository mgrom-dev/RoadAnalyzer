package ru.mgrom.roadanalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<String> auth(@RequestParam String login, @RequestParam String pswd,
            @RequestParam(required = false) String email, HttpServletRequest request) {

        if (email != null && !email.isBlank()) {
            // registration
            AuthStatus status = userService.register(login, email, pswd, request);

            return switch (status) {
                case SUCCESS -> responseCreated("Пользователь успешно зарегестрирован.");
                case USER_ALREADY_EXISTS -> responseConflict("Пользователь с таким логином уже зарегистрирован.");
                case EMAIL_ALREADY_EXISTS -> responseConflict("Пользователь с таким email уже зарегистрирован.");
                default -> responseServerError("Произошла ошибка. Попробуйте позже.");
            };
        } else {
            // authorization
            AuthStatus status = userService.authorize(login, pswd, request);

            return switch (status) {
                case SUCCESS -> responseFound();
                case PASSWORD_INCORRECT -> responseUnauthorized("Неверный логин или пароль.");
                case USER_NOT_FOUND -> responseUnauthorized("Пользователь не найден.");
                default -> responseServerError("Произошла ошибка. Попробуйте позже.");
            };
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("code") String code, HttpServletRequest request) {
        User user = userRepository.findByVerificationCode(code);
        
        if (user != null) {
            user.setEnabled(true); // Enable the user account after verification
            user.setVerificationCode(null); // Clear the verification code after use
            userRepository.save(user);
            return ResponseEntity.ok("Your email has been successfully verified.");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification code.");
        }
    }

    private ResponseEntity<String> responseEntity(HttpStatus httpStatus, String body) {
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(body);
    }

    private ResponseEntity<String> responseCreated(String message) {
        return responseEntity(HttpStatus.CREATED, String.format("{\"success\": \"%s\"}", message));
    }

    private ResponseEntity<String> responseConflict(String message) {
        return responseEntity(HttpStatus.CONFLICT, String.format("{\"message\": \"%s\"}", message));
    }

    private ResponseEntity<String> responseServerError(String message) {
        return responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, String.format("{\"message\": \"%s\"}", message));
    }

    private ResponseEntity<String> responseFound() {
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/").build();
    }

    private ResponseEntity<String> responseUnauthorized(String message) {
        return responseEntity(HttpStatus.UNAUTHORIZED, String.format("{\"message\": \"%s\"}", message));
    }
}
