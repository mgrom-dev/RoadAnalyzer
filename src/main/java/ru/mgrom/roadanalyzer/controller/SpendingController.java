package ru.mgrom.roadanalyzer.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.mgrom.roadanalyzer.model.Spending;
import ru.mgrom.roadanalyzer.service.SpendingService;

@RestController
@RequestMapping("/api/spending")
public class SpendingController {

    private final SpendingService spendingService;

    public SpendingController(SpendingService spendingService) {
        this.spendingService = spendingService;
    }

    @GetMapping
    public ResponseEntity<List<Spending>> getAll(@RequestParam(required = false) LocalDate createdAtBefore,
            @RequestParam(required = false) LocalDate createdAtAfter) {
        return ResponseEntity.ok(spendingService.getAll(createdAtBefore, createdAtAfter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spending> get(@PathVariable Long id) {
        return spendingService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Spending> create(@RequestBody Spending spending) {
        final Spending created = spendingService.create(spending);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        spendingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
