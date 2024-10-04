package ru.mgrom.roadanalyzer.controller;

import java.time.LocalDate;
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
import ru.mgrom.roadanalyzer.repository.SpendingRepository;
import ru.mgrom.roadanalyzer.service.SessionUtils;
import ru.mgrom.roadanalyzer.dto.SessionResponse;

@RestController
public class SessionController {
    private static int emailPrefix = 1;

    @Autowired
    private ApplicationContext applicationContext;

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

    // create test data for developing
    private void testData(String databaseIdentifier) {
        if (emailPrefix != 2) {
            return;
        }

        SpendingRepository spendingRepository = applicationContext.getBean(SpendingRepository.class);
        ExpenseTypeRepository expTypeRepository = applicationContext.getBean(ExpenseTypeRepository.class);
        PartAndServiceRepository partRepository = applicationContext.getBean(PartAndServiceRepository.class);

        Stream.of("топливо", "услуги", "запчасти").forEach(description -> {
            ExpenseType expenseType = new ExpenseType();
            expenseType.setDescription(description);
            expTypeRepository.save(expenseType, databaseIdentifier);
        });

        Long[] typeId = { 1L };
        Stream.of("Автобензин АИ-95", "Мойка машины", "Тормозные колодки передние комплект TRW GDB2108")
                .forEach(description -> {
                    PartAndService partAndService = new PartAndService();
                    partAndService.setDescription(description);
                    partAndService.setType(typeId[0]++);
                    partRepository.save(partAndService, databaseIdentifier);
                });

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
        spendingRepository.save(spending1, databaseIdentifier);
        spendingRepository.save(spending2, databaseIdentifier);
        spendingRepository.save(spending3, databaseIdentifier);
    }
}