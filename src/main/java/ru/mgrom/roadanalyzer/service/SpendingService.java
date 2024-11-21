package ru.mgrom.roadanalyzer.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ru.mgrom.roadanalyzer.dto.SpendingDTO;
import ru.mgrom.roadanalyzer.model.Spending;
import ru.mgrom.roadanalyzer.repository.SpendingRepository;

@Service
public class SpendingService extends BaseService<Spending, SpendingRepository> implements ServiceInterface<Spending> {

    public List<Spending> getAll(LocalDate createdAtBefore, LocalDate createdAtAfter, String databaseIdentifier) {
        createdAtBefore = createdAtBefore == null ? LocalDate.MIN : createdAtBefore;
        createdAtAfter = createdAtAfter == null ? LocalDate.MAX : createdAtAfter;
        return repository.findSpendingsByDateRange(createdAtAfter, createdAtBefore, databaseIdentifier);
    }

    public List<SpendingDTO> getAllExpanded(LocalDate createdAtBefore, LocalDate createdAtAfter,
            String databaseIdentifier) {
        createdAtBefore = createdAtBefore == null ? LocalDate.MIN : createdAtBefore;
        createdAtAfter = createdAtAfter == null ? LocalDate.MAX : createdAtAfter;
        return repository.findSpendingsByDateRangeDTO(createdAtAfter, createdAtBefore, databaseIdentifier);
    }

}
