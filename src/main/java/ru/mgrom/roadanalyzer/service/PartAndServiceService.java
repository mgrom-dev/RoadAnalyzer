package ru.mgrom.roadanalyzer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ru.mgrom.roadanalyzer.repository.PartAndServiceRepository;
import ru.mgrom.roadanalyzer.model.PartAndService;

@Service
public class PartAndServiceService {
    private final PartAndServiceRepository partAndServiceRepository;

    public PartAndServiceService(PartAndServiceRepository partAndServiceRepository) {
        this.partAndServiceRepository = partAndServiceRepository;
    }

    public Optional<PartAndService> getById(Long id, String databaseIdentifier) {
        return partAndServiceRepository.findById(id, databaseIdentifier);
    }

    public List<PartAndService> getAll(String databaseIdentifier) {
        return partAndServiceRepository.findAll(databaseIdentifier);
    }

    public boolean create(PartAndService partAndService, String databaseIdentifier) {
        return partAndServiceRepository.save(partAndService, databaseIdentifier);
    }

    public void delete(PartAndService partAndService, String databaseIdentifier) {
        partAndServiceRepository.delete(partAndService, databaseIdentifier);
    }

    public boolean update(Long id, PartAndService partAndService, String databaseIdentifier) {
        partAndService.setId(id);
        return partAndServiceRepository.save(partAndService, databaseIdentifier);
    }
}
