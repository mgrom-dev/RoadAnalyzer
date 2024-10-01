package ru.mgrom.roadanalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.mgrom.roadanalyzer.model.Session;
import ru.mgrom.roadanalyzer.model.User;
import ru.mgrom.roadanalyzer.repository.SessionRepository;
import ru.mgrom.roadanalyzer.repository.UserRepository;
import ru.mgrom.roadanalyzer.dto.SessionResponse;

@RestController
public class SessionController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @PersistenceContext
    private EntityManager entityManager; // Используем EntityManager для выполнения SQL

    @Transactional
    @GetMapping("/session-info")
    public SessionResponse startSession(HttpServletRequest request) {
        HttpSession session = request.getSession(); // Получаем текущую сессию
        String sessionId = session.getId(); // Получаем ID сессии

        // Проверяем, существует ли сессия в базе данных
        Session existingSession = sessionRepository.findBySessionId(sessionId);
        if (existingSession == null) {
            // Сессия не найдена, создаем нового пользователя
            User newUser = new User();
            newUser.setUsername("defaultUser"); // Укажите логин по умолчанию или получите его из запроса
            newUser.setPassword("defaultPassword"); // Укажите пароль по умолчанию или получите его из запроса
            newUser.setEmail("user@example.com"); // Укажите email по умолчанию или получите его из запроса
            newUser.setDatabaseIdentifier("user_db_" + sessionId); // Уникальный идентификатор базы данных

            // Создаем схему, если она не существует
            createSchemaIfNotExists(newUser.getDatabaseIdentifier());

            userRepository.save(newUser); // Сохраняем пользователя в базе данных

            // Создаем новую запись сессии
            Session newSession = new Session();
            newSession.setSessionId(sessionId);
            newSession.setUserId(newUser.getId()); // Сохраняем ID нового пользователя
            sessionRepository.save(newSession); // Сохраняем сессию в базе данных

            return new SessionResponse(
                    newUser.getId(),
                    sessionId,
                    newUser.getEmail(),
                    newUser.getDatabaseIdentifier(),
                    newSession.getCreatedAt(),
                    newSession.isActive());
        } else {
            // Если сессия уже существует, возвращаем информацию о ней
            User existingUser = userRepository.findById(existingSession.getUserId()).orElse(null);
            if (existingUser != null) {
                return new SessionResponse(
                        existingUser.getId(),
                        existingSession.getSessionId(),
                        existingUser.getEmail(),
                        existingUser.getDatabaseIdentifier(),
                        existingSession.getCreatedAt(),
                        existingSession.isActive());
            } else {
                throw new RuntimeException("Пользователь не найден для существующей сессии.");
            }
        }
    }
    
    private void createSchemaIfNotExists(String schemaName) {
        String sql = "CREATE SCHEMA IF NOT EXISTS \"" + schemaName + "\"";
        entityManager.createNativeQuery(sql).executeUpdate();
    }
}