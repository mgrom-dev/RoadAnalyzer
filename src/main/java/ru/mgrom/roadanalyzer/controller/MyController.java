package ru.mgrom.roadanalyzer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController // Используем RestController для автоматического добавления @ResponseBody
public class MyController {

    @GetMapping("/session-info")
    public String getSessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(); // Получаем текущую сессию
        String sessionId = session.getId(); // Получаем ID сессии

        // Сохраняем данные в сессии
        session.setAttribute("someAttribute", "someValue");

        // Возвращаем ID сессии как JSON
        return "{\"sessionId\": \"" + sessionId + "\", \"someAttribute\": \"" + session.getAttribute("someAttribute")
                + "\"}";
    }
}