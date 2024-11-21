package ru.mgrom.roadanalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.model.User;
import ru.mgrom.roadanalyzer.repository.UserRepository;
import ru.mgrom.roadanalyzer.service.SessionUtils;
import ru.mgrom.roadanalyzer.service.UserService;
import ru.mgrom.roadanalyzer.service.UserService.AuthStatus;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        userService.logout(request);
        return "redirect:/";
    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestParam String login, @RequestParam String pswd,
            @RequestParam(required = false) String email, HttpServletRequest request) {

        if (email != null && !email.isBlank()) {
            // registration
            AuthStatus status = AuthStatus.SUCCESS;
            try {
                status = userService.register(login, email, pswd, request);
            } catch (MailException exception) {
                // if the user is successfully registered, but sending an email with a
                // confirmation code is not available.
                exception.printStackTrace();
                userService.authorize(login, pswd, request);
                return responseRedirect("/");
            }

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
                case SUCCESS -> responseRedirect("/");
                case PASSWORD_INCORRECT -> responseUnauthorized("Неверный логин или пароль.");
                case USER_NOT_FOUND -> responseUnauthorized("Пользователь не найден.");
                default -> responseServerError("Произошла ошибка. Попробуйте позже.");
            };
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("confirmationCode") String code,
            HttpServletRequest request) {
        User user = SessionUtils.getUser(request);

        if (user.getVerificationCode().equals(code)) {
            user.setActive(true);
            user.setVerificationCode(null);
            userRepository.save(user);
            return responseOk();
        } else {
            return responseBadRequest("Не правильный код верификации.");
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

    private ResponseEntity<String> responseBadRequest(String message) {
        return responseEntity(HttpStatus.BAD_REQUEST, String.format("{\"message\": \"%s\"}", message));
    }

    private ResponseEntity<String> responseOk() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private ResponseEntity<String> responseServerError(String message) {
        return responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, String.format("{\"message\": \"%s\"}", message));
    }

    private ResponseEntity<String> responseRedirect(String location) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).header("Location", location)
                .body(String.format("{\"redirect\": \"%s\"}", location));
    }

    private ResponseEntity<String> responseUnauthorized(String message) {
        return responseEntity(HttpStatus.UNAUTHORIZED, String.format("{\"message\": \"%s\"}", message));
    }
}
