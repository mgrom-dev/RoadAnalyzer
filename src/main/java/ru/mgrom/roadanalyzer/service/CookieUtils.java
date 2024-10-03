package ru.mgrom.roadanalyzer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.model.Session;
import ru.mgrom.roadanalyzer.model.User;
import ru.mgrom.roadanalyzer.repository.SessionRepository;
import ru.mgrom.roadanalyzer.repository.UserRepository;

@Component
public class CookieUtils {
    private static final String SESSION_COOKIE_NAME = "JSESSIONID";
    private static SessionRepository sessionRepository;
    private static UserRepository userRepository;

    @Autowired
    public CookieUtils(SessionRepository sessionRepository, UserRepository userRepository) {
        CookieUtils.sessionRepository = sessionRepository;
        CookieUtils.userRepository = userRepository;
    }

    public static String getSessionId(HttpServletRequest request) {
        String cookieHeader = request.getHeader("Cookie");
        String sessionId = null;

        if (cookieHeader != null) {
            String[] cookies = cookieHeader.split("; ");
            for (String cookie : cookies) {
                if (cookie.startsWith(SESSION_COOKIE_NAME + "=")) {
                    sessionId = cookie.substring((SESSION_COOKIE_NAME + "=").length());
                    break;
                }
            }
        }

        return sessionId;
    }

    public static Optional<User> getUser(HttpServletRequest request) {
        Optional<User> user = Optional.empty();
        Session session = sessionRepository.findBySessionId(getSessionId(request));

        if (session != null) {
            user = userRepository.findById(session.getUserId());
        }

        return user;
    }
}