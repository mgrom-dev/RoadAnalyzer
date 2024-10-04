package ru.mgrom.roadanalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.service.UserService;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @PostMapping("/auth")
    public RedirectView auth(@RequestParam String login, @RequestParam String pswd,
            @RequestParam(required = false) String email, HttpServletRequest request) {

        if (email != null && !email.isEmpty()) {
            // Логика регистрации
            System.out.println("Registation: Username: " + login + ", Password: " + pswd + ", Email: " + email);
        } else {
            userService.authorize(login, pswd, request);
            System.out.println("Authorization: Username: " + login + ", Password: " + pswd);
        }
        return new RedirectView("/");
    }
}
