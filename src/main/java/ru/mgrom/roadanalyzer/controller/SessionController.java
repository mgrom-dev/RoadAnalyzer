package ru.mgrom.roadanalyzer.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.model.ExpenseType;
import ru.mgrom.roadanalyzer.model.PartAndService;
import ru.mgrom.roadanalyzer.model.Session;
import ru.mgrom.roadanalyzer.model.Spending;
import ru.mgrom.roadanalyzer.model.User;
import ru.mgrom.roadanalyzer.repository.ExpenseTypeRepository;
import ru.mgrom.roadanalyzer.repository.PartAndServiceRepository;
import ru.mgrom.roadanalyzer.repository.SessionRepository;
import ru.mgrom.roadanalyzer.repository.SpendingRepository;
import ru.mgrom.roadanalyzer.repository.UserRepository;
import ru.mgrom.roadanalyzer.service.DatabaseSchemaService;
import ru.mgrom.roadanalyzer.dto.SessionResponse;

@RestController
public class SessionController {
    private static int userNamePrefix = 1;
    private static int emailPrefix = 1;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DatabaseSchemaService databaseSchemaService;

    @GetMapping("/session-info")
    public SessionResponse startSession(HttpServletRequest request) {
        String sessionId = request.getSession().getId(); // Получаем ID сессии

        // Проверяем, существует ли сессия в базе данных
        Session existingSession = sessionRepository.findBySessionId(sessionId);
        if (existingSession == null) {
            // Сессия не найдена, создаем нового пользователя
            User newUser = new User();
            newUser.setUsername("defaultUser" + userNamePrefix++); // Укажите логин по умолчанию или получите его из
                                                                   // запроса
            newUser.setPassword("defaultPassword"); // Укажите пароль по умолчанию или получите его из запроса
            newUser.setEmail("user@example.com" + emailPrefix++); // Укажите email по умолчанию или получите его из
                                                                  // запроса
            newUser.setDatabaseIdentifier("user_db_" + sessionId); // Уникальный идентификатор базы данных

            userRepository.save(newUser); // Сохраняем пользователя в базе данных

            // Создаем схему, если она не существует
            databaseSchemaService.createSchemaIfNotExists(newUser.getDatabaseIdentifier());

            // Создаем новую запись сессии
            Session newSession = new Session();
            newSession.setSessionId(sessionId);
            newSession.setUserId(newUser.getId()); // Сохраняем ID нового пользователя
            sessionRepository.save(newSession); // Сохраняем сессию в базе данных

            testData();

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

    // create test data for developing
    private void testData() {
        if (userNamePrefix != 2) {
            return;
        }

        SpendingRepository spendingRepository = applicationContext.getBean(SpendingRepository.class);
        ExpenseTypeRepository expTypeRepository = applicationContext.getBean(ExpenseTypeRepository.class);
        PartAndServiceRepository partRepository = applicationContext.getBean(PartAndServiceRepository.class);

        Stream.of("топливо", "услуги", "запчасти").forEach(description -> {
            ExpenseType expenseType = new ExpenseType();
            expenseType.setDescription(description);
            expTypeRepository.save(expenseType);
        });

        Long[] typeId = { 1L };
        List<PartAndService> partAndServices = Stream
                .of("Автобензин АИ-95", "Мойка машины", "Тормозные колодки передние комплект TRW GDB2108")
                .map(description -> {
                    PartAndService partAndService = new PartAndService();
                    partAndService.setDescription(description);
                    partAndService.setType(typeId[0]++);
                    return partAndService;
                }).collect(Collectors.toList());
        partRepository.saveAll(partAndServices);
        Spending spending1 = new Spending();
        spending1.setDate(LocalDate.of(2024, 6, 16));
        spending1.setPartAndServiceId(1L);
        spending1.setCount(30.0);
        spending1.setAmount(1615.15);
        Spending spending2 = new Spending();
        spending2.setDate(LocalDate.of(2024, 6, 20));
        spending2.setPartAndServiceId(2L);
        spending2.setDescription("кузов + коврики");
        spending2.setCount(1.0);
        spending2.setAmount(100.15);
        Spending spending3 = new Spending();
        spending3.setDate(LocalDate.of(2024, 6, 25));
        spending3.setPartAndServiceId(3L);
        spending3.setCount(1.0);
        spending3.setAmount(2400.0);
        spendingRepository.createSpending(spending1);
        spendingRepository.createSpending(spending2);
        spendingRepository.createSpending(spending3);
    }
}