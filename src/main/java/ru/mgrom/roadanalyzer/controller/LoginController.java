package ru.mgrom.roadanalyzer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {

    @PostMapping("/auth")
    public RedirectView auth(@RequestParam String login, @RequestParam String pswd,
            @RequestParam(required = false) String email) {

        if (email != null && !email.isEmpty()) {
            // Логика регистрации
            System.out.println("Registation: Username: " + login + ", Password: " + pswd + ", Email: " + email);
        } else {
            // Логика авторизации
            System.out.println("Authorization: Username: " + login + ", Password: " + pswd);
        }
        return new RedirectView("/");
    }
}
