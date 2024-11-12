package ru.mgrom.roadanalyzer.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ru.mgrom.roadanalyzer.dto.SpendingDTO;
import ru.mgrom.roadanalyzer.model.Spending;
import ru.mgrom.roadanalyzer.repository.SpendingRepository;

@Service
public class SpendingService {
    private final SpendingRepository spendingRepository;

    public SpendingService(SpendingRepository spendingRepository) {
        this.spendingRepository = spendingRepository;
    }

    public Optional<Spending> getById(Long id, String databaseIdentifier) {
        return spendingRepository.findById(id, databaseIdentifier);
    }

    public List<Spending> getAll(LocalDate createdAtBefore, LocalDate createdAtAfter, String databaseIdentifier) {
        createdAtBefore = createdAtBefore == null ? LocalDate.MIN : createdAtBefore;
        createdAtAfter = createdAtAfter == null ? LocalDate.MAX : createdAtAfter;
        return spendingRepository.findSpendingsByDateRange(createdAtAfter, createdAtBefore, databaseIdentifier);
    }

    public List<SpendingDTO> getAllExpanded(LocalDate createdAtBefore, LocalDate createdAtAfter,
            String databaseIdentifier) {
        createdAtBefore = createdAtBefore == null ? LocalDate.MIN : createdAtBefore;
        createdAtAfter = createdAtAfter == null ? LocalDate.MAX : createdAtAfter;
        return spendingRepository.findSpendingsByDateRangeDTO(createdAtAfter, createdAtBefore, databaseIdentifier);
    }

    public boolean create(Spending spending, String databaseIdentifier) {
        return spendingRepository.save(spending, databaseIdentifier);
    }

    public boolean update(Spending spending, String databaseIdentifier) {
        return spendingRepository.update(spending, databaseIdentifier);
    }

    public void delete(Long spendingId, String databaseIdentifier) {
        spendingRepository.deleteById(spendingId, databaseIdentifier);
    }
}
