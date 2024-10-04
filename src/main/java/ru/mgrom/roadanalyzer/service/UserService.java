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
    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    public void authorize(String login, String password, HttpServletRequest request) {
        User user = userRepository.findByUsername(login);
        if (user != null && user.getPassword().equals(password)) {
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
        } else if (user == null) {
            System.out.println("login not found");
        } else {
            System.out.println("password incorrect");
        }
    }

    private boolean checkPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
