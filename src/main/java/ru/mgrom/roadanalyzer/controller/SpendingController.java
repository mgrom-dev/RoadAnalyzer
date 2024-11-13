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
import ru.mgrom.roadanalyzer.dto.IdResponse;
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

    public SpendingController(SpendingService spendingService, ExpenseTypeService expenseTypeService,
            PartAndServiceService partAndServiceService) {
        this.spendingService = spendingService;
        this.expenseTypeService = expenseTypeService;
        this.partAndServiceService = partAndServiceService;
    }

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
    public ResponseEntity<IdResponse> create(@RequestBody SpendingDTO spendingDTO, HttpServletRequest request) {
        if (spendingDTO.getPartAndServiceId() == null) { // new part and service
            if (spendingDTO.getPartDescription().isBlank()) { // description not set, error
                IdResponse response = new IdResponse("Failed to create spending: part and service not set", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // checking for a new part type
            String newTypeDescription = spendingDTO.getPartTypeDescription();
            if (spendingDTO.getPartType() == null && !newTypeDescription.isBlank()) {
                expenseTypeService.create(new ExpenseType(newTypeDescription), SessionUtils.getDatabaseId(request));
                expenseTypeService.getByDescription(newTypeDescription, SessionUtils.getDatabaseId(request))
                        .ifPresent(expenseType -> {
                            spendingDTO.setPartType(expenseType.getId());
                        });
            } else {
                spendingDTO.setPartType(3L); // default type - spare parts
            }

            // create new part and service
            PartAndService newPartAndService = new PartAndService(spendingDTO.getPartDescription(),
                    spendingDTO.getPartType());
            partAndServiceService.create(newPartAndService, SessionUtils.getDatabaseId(request));
            Optional<PartAndService> partAndServiceOptional = partAndServiceService.get(newPartAndService,
                    SessionUtils.getDatabaseId(request));
            if (partAndServiceOptional.isPresent()) {
                spendingDTO.setPartAndServiceId(partAndServiceOptional.get().getId());
            } else {
                IdResponse response = new IdResponse("Failed to create spending: part and service not created", null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }

        Spending spending = new Spending();
        spending.setDate(spendingDTO.getDate());
        spending.setPartAndServiceId(spendingDTO.getPartAndServiceId());
        spending.setDescription(spendingDTO.getDescription());
        spending.setCount(spendingDTO.getCount());
        spending.setAmount(spendingDTO.getAmount());

        Long savedId = 0L;
        if (spendingDTO.getId() != null) {
            spending.setId(spendingDTO.getId());
            savedId = spendingDTO.getId();
            spendingService.update(spending, SessionUtils.getDatabaseId(request));
        } else {
            savedId = spendingService.create(spending, SessionUtils.getDatabaseId(request));
        }

        if (savedId > 0) {
            IdResponse response = new IdResponse("Spending saved successfully", savedId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            IdResponse response = new IdResponse("Failed to save spending: Invalid data or conflict", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        spendingService.delete(id, SessionUtils.getDatabaseId(request));
        return ResponseEntity.noContent().build();
    }
}
