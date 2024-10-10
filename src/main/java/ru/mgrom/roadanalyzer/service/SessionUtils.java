package ru.mgrom.roadanalyzer.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.model.Session;
import ru.mgrom.roadanalyzer.model.User;
import ru.mgrom.roadanalyzer.repository.SessionRepository;
import ru.mgrom.roadanalyzer.repository.UserRepository;

@Component
public class SessionUtils {
    private static int createdUsers = 1;
    private static SessionRepository sessionRepository;
    private static UserRepository userRepository;
    private static DatabaseSchemaService databaseSchemaService;

    @Autowired
    public SessionUtils(SessionRepository sessionRepository, UserRepository userRepository,
            DatabaseSchemaService databaseSchemaService) {
        SessionUtils.sessionRepository = sessionRepository;
        SessionUtils.userRepository = userRepository;
        SessionUtils.databaseSchemaService = databaseSchemaService;
    }

    public static String getSessionId(HttpServletRequest request) {
        return request.getSession().getId();
    }

    public static User getUser(HttpServletRequest request) {
        Optional<Session> findedSession = Optional.ofNullable(sessionRepository.findBySessionId(getSessionId(request)));
        Optional<User> findedUser = userRepository.findById(findedSession.orElse(new Session()).getUserId());
        User user = null;

        if (findedUser.isPresent()) {
            user = findedUser.get();
        } else {
            user = createTempUser();
        }

        if (findedSession.isEmpty()) {
            Session session = new Session();
            session.setSessionId(getSessionId(request));
            session.setUserId(user.getId());
            session.setCreatedAt(LocalDateTime.now());
            session.setLastAccessedAt(LocalDateTime.now());
            sessionRepository.save(session);
        }

        return user;
    }

    public static Session getSession(HttpServletRequest request) {
        getUser(request); // call for create user and session if not exist
        return sessionRepository.findBySessionId(getSessionId(request));
    }

    private static User createTempUser() {
        User tempUser = new User();

        String userName = "defaultUser_" + createdUsers;
        String databaseIdentifier = "user_db_" + createdUsers;

        tempUser.setUsername(userName);
        tempUser.setDatabaseIdentifier(databaseIdentifier);
        tempUser.setEmail("user@example.com_" + createdUsers);
        tempUser.setPassword("defaultPassword");
        tempUser.setRole("user");
        tempUser.setActive(false);
        tempUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(tempUser);
        createdUsers++;
        databaseSchemaService.createSchemaIfNotExists(databaseIdentifier);

        tempUser = userRepository.findByUsername(userName);
        return tempUser;
    }

    public static String getDatabaseId(HttpServletRequest request) {
        return getUser(request).getDatabaseIdentifier();
    }
}