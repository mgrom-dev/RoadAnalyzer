package ru.mgrom.roadanalyzer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ru.mgrom.roadanalyzer.model.Spending;
import ru.mgrom.roadanalyzer.repository.SpendingRepository;

@Service
public class SpendingService {
    private final SpendingRepository spendingRepository;

    public SpendingService(SpendingRepository spendingRepository) {
        this.spendingRepository = spendingRepository;
    }

    public Optional<Spending> getById(Long id) {
        return spendingRepository.findById(id);
    }

    public List<Spending> getAll() {
        return spendingRepository.findAll();
    }

    public Spending create(Spending spending) {
        return spendingRepository.save(spending);
    }

    public void delete(Spending spending) {
        spendingRepository.delete(spending);
    }

    public void delete(Long id) {
        spendingRepository.deleteById(id);
    }

    public Optional<Spending> update(Long id, Spending spending) {
        spending.setId(id);
        return Optional.of(spendingRepository.save(spending));
    }
}
