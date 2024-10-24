package ru.mgrom.roadanalyzer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.model.ExpenseType;
import ru.mgrom.roadanalyzer.service.ExpenseTypeService;
import ru.mgrom.roadanalyzer.service.SessionUtils;

@RestController
@RequestMapping("/api/expense_type")
public class ExpenseTypeController {
    private final ExpenseTypeService expenseTypeService;

    public ExpenseTypeController(ExpenseTypeService expenseTypeService) {
        this.expenseTypeService = expenseTypeService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseType>> getAll(HttpServletRequest request) {
        return ResponseEntity.ok(expenseTypeService.getAll(SessionUtils.getDatabaseId(request)));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ExpenseType expenseType, HttpServletRequest request) {
        boolean isCreated = expenseTypeService.create(expenseType, SessionUtils.getDatabaseId(request));
        if (isCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Expense type created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create expense type: Invalid data or conflict");
        }
    }
}
