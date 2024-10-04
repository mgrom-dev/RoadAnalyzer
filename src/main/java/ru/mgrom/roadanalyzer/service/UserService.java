package ru.mgrom.roadanalyzer.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.model.Session;
import ru.mgrom.roadanalyzer.model.User;
import ru.mgrom.roadanalyzer.repository.SessionRepository;
import ru.mgrom.roadanalyzer.repository.UserRepository;

@Service
public class UserService {

    public static enum AuthStatus {
        SUCCESS,
        USER_NOT_FOUND,
        PASSWORD_INCORRECT,
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    public AuthStatus authorize(String login, String password, HttpServletRequest request) {
        User user = userRepository.findByUsername(login);
        AuthStatus authStatus = AuthStatus.SUCCESS;
        if (user == null) {
            authStatus = AuthStatus.USER_NOT_FOUND;
        } else if (!checkPassword(password, user.getPassword())) {
            authStatus = AuthStatus.PASSWORD_INCORRECT;
        } else {
            String sessionId = SessionUtils.getSessionId(request);
            if (sessionId != null) {
                Session session = sessionRepository.findBySessionId(sessionId);
                if (session == null) {
                    session = new Session();
                    session.setSessionId(sessionId);
                    session.setCreatedAt(LocalDateTime.now());
                    session.setActive(true);
                }
                session.setUserId(user.getId());
                session.setLastAccessedAt(LocalDateTime.now());
                sessionRepository.save(session);
            }
        }
        return authStatus;
    }

    private boolean checkPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
