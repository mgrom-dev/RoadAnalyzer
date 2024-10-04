package ru.mgrom.roadanalyzer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.model.Session;
import ru.mgrom.roadanalyzer.model.User;
import ru.mgrom.roadanalyzer.service.SessionUtils;
import ru.mgrom.roadanalyzer.dto.SessionResponse;

@RestController
public class SessionController {

    @GetMapping("/session-info")
    public SessionResponse startSession(HttpServletRequest request) {
        User user = SessionUtils.getUser(request);
        Session session = SessionUtils.getSession(request);

        return new SessionResponse(
                user.getId(),
                session.getSessionId(),
                user.getEmail(),
                user.getDatabaseIdentifier(),
                user.getCreatedAt(),
                user.isActive());
    }
}