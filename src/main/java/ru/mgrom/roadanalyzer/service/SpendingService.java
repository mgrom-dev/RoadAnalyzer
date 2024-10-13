package ru.mgrom.roadanalyzer.service;

import java.time.LocalDate;
import java.util.List;

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

    // public Optional<Spending> getById(Long id) {
    // return spendingRepository.findById(id);
    // }

    // public List<Spending> getAll(LocalDate createdAtBefore, LocalDate
    // createdAtAfter) {
    // createdAtBefore = createdAtBefore == null ? LocalDate.MIN : createdAtBefore;
    // createdAtAfter = createdAtAfter == null ? LocalDate.MAX : createdAtAfter;
    // return spendingRepository.findByDateBetween(createdAtAfter, createdAtBefore);
    // }

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

    // public Spending create(Spending spending) {
    // return spendingRepository.save(spending);
    // }

    public void delete(Long spendingId, String databaseIdentifier) {
        spendingRepository.deleteById(spendingId, databaseIdentifier);
    }

    // public void delete(Long id) {
    // spendingRepository.deleteById(id);
    // }

    // public Optional<Spending> update(Long id, Spending spending) {
    // spending.setId(id);
    // return Optional.of(spendingRepository.save(spending));
    // }
}
