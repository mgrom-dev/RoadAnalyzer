package ru.mgrom.roadanalyzer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.model.PartAndService;
import ru.mgrom.roadanalyzer.service.PartAndServiceService;
import ru.mgrom.roadanalyzer.service.SessionUtils;

@RestController
@RequestMapping("/api/part_and_service")
public class PartAndServiceController {
    private final PartAndServiceService partAndServiceService;

    public PartAndServiceController(PartAndServiceService partAndServiceService) {
        this.partAndServiceService = partAndServiceService;
    }

    @GetMapping
    public ResponseEntity<List<PartAndService>> getAll(HttpServletRequest request) {
        return ResponseEntity.ok(partAndServiceService.getAll(SessionUtils.getDatabaseId(request)));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody PartAndService partAndService, HttpServletRequest request) {
        boolean isCreated = partAndServiceService.create(partAndService, SessionUtils.getDatabaseId(request));
        if (isCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Part and service created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create part and service: Invalid data or conflict");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        partAndServiceService.delete(id, SessionUtils.getDatabaseId(request));
        return ResponseEntity.noContent().build();
    }
}
