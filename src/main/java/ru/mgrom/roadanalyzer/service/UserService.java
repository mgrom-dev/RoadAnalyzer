package ru.mgrom.roadanalyzer.service;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        USER_ALREADY_EXISTS,
        EMAIL_ALREADY_EXISTS
    }

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Value("${spring.mail.verification-required}")
    private boolean verificationRequired;

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

                // if verification is disabled
                if (!verificationRequired) {
                    user.setActive(true);
                    userRepository.save(user);
                }
            }
        }
        return authStatus;
    }

    public AuthStatus register(String login, String email, String password, HttpServletRequest request) {
        if (userRepository.findByUsername(login) != null) {
            return AuthStatus.USER_ALREADY_EXISTS;
        }
        if (userRepository.findByEmail(email) != null) {
            return AuthStatus.EMAIL_ALREADY_EXISTS;
        }

        User user = SessionUtils.getUser(request);
        user.setUsername(login);
        user.setEmail(email);
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encodedPassword);

        // Generate verification code
        String verificationCode = RandomStringUtils.secure().nextAlphanumeric(7);
        user.setVerificationCode(verificationCode);

        userRepository.save(user);

        if (verificationRequired) {
            emailService.sendEmail(email, "Email Verification Code", "Your verification code is: " + verificationCode);
        }

        return AuthStatus.SUCCESS;
    }

    public void logout(HttpServletRequest request) {
        SessionUtils.logout(request);
    }

    private boolean checkPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
