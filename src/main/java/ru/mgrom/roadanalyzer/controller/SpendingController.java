package ru.mgrom.roadanalyzer.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.dto.SpendingDTO;
import ru.mgrom.roadanalyzer.model.ExpenseType;
import ru.mgrom.roadanalyzer.model.PartAndService;
import ru.mgrom.roadanalyzer.model.Spending;
import ru.mgrom.roadanalyzer.service.SessionUtils;
import ru.mgrom.roadanalyzer.service.SpendingService;
import ru.mgrom.roadanalyzer.service.ExpenseTypeService;
import ru.mgrom.roadanalyzer.service.PartAndServiceService;

@RestController
@RequestMapping("/api/spending")
public class SpendingController {

    private final SpendingService spendingService;
    private final ExpenseTypeService expenseTypeService;
    private final PartAndServiceService partAndServiceService;

    public SpendingController(SpendingService spendingService, ExpenseTypeService expenseTypeService, PartAndServiceService partAndServiceService) {
        this.spendingService = spendingService;
        this.expenseTypeService = expenseTypeService;
        this.partAndServiceService = partAndServiceService;
    }

    // @GetMapping
    // public ResponseEntity<List<Spending>> getAll(@RequestParam(required = false)
    // LocalDate createdAtBefore,
    // @RequestParam(required = false) LocalDate createdAtAfter) {
    // return ResponseEntity.ok(spendingService.getAll(createdAtBefore,
    // createdAtAfter));
    // }

    @GetMapping
    public ResponseEntity<List<Spending>> getAll(@RequestParam(required = false) LocalDate createdAtBefore,
            @RequestParam(required = false) LocalDate createdAtAfter, HttpServletRequest request) {
        return ResponseEntity
                .ok(spendingService.getAll(createdAtBefore, createdAtAfter, SessionUtils.getDatabaseId(request)));
    }

    @GetMapping("/full")
    public ResponseEntity<List<SpendingDTO>> getAllDTO(@RequestParam(required = false) LocalDate createdAtBefore,
            @RequestParam(required = false) LocalDate createdAtAfter, HttpServletRequest request) {
        return ResponseEntity
                .ok(spendingService.getAllExpanded(createdAtBefore, createdAtAfter,
                        SessionUtils.getDatabaseId(request)));
    }
    // @GetMapping("/{id}")
    // public ResponseEntity<Spending> get(@PathVariable Long id) {
    // return spendingService.getById(id)
    // .map(ResponseEntity::ok)
    // .orElseGet(() -> ResponseEntity.notFound().build());
    // }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody SpendingDTO spendingDTO, HttpServletRequest request) {
        System.out.println(spendingDTO);
        if (spendingDTO.getPartAndServiceId() == null) {
            String partDescription = spendingDTO.getPartDescription();
            if (partDescription.isEmpty() || partDescription.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create spending: part and service not set");
            } else {
                String partTypeDescription = spendingDTO.getPartTypeDescription();
                Long partTypeId = 3L;
                if (spendingDTO.getPartType() == null && !partTypeDescription.isEmpty() && !partTypeDescription.isBlank()) {
                    ExpenseType expenseType = new ExpenseType();
                    expenseType.setDescription(partTypeDescription);
                    expenseTypeService.create(expenseType, SessionUtils.getDatabaseId(request));
                    Optional<ExpenseType> expOptional = expenseTypeService.getByDescription(partTypeDescription, SessionUtils.getDatabaseId(request));
                    if (expOptional.isPresent()) {
                        expenseType = expOptional.get();
                    }
                    partTypeId = expenseType.getId();
                    spendingDTO.setPartType(partTypeId);
                }
                
                PartAndService partAndService = new PartAndService();
                partAndService.setDescription(partDescription);
                partAndService.setType(partTypeId);
                partAndServiceService.create(partAndService, SessionUtils.getDatabaseId(request));
                Optional<PartAndService> parOptional = partAndServiceService.getByDescription(partDescription, SessionUtils.getDatabaseId(request));
                if (parOptional.isPresent()) {
                    partAndService = parOptional.get();
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body("Failed to create spending: Failed to create part and service");
                }
                spendingDTO.setPartAndServiceId(partAndService.getId());
            }
        }
        Spending spending = new Spending();
        spending.setDate(spendingDTO.getDate());
        spending.setPartAndServiceId(spendingDTO.getPartAndServiceId());
        spending.setDescription(spendingDTO.getDescription());
        spending.setCount(spendingDTO.getCount());
        spending.setAmount(spendingDTO.getAmount());
        boolean isCreated = spendingService.create(spending, SessionUtils.getDatabaseId(request));
        if (isCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Spending created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create spending: Invalid data or conflict");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        spendingService.delete(id, SessionUtils.getDatabaseId(request));
        return ResponseEntity.noContent().build();
    }
}
