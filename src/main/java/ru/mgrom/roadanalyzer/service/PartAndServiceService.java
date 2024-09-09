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

    public Optional<PartAndService> getById(Long id) {
        return partAndServiceRepository.findById(id);
    }

    public List<PartAndService> getAll() {
        return partAndServiceRepository.findAll();
    }

    public PartAndService create(PartAndService partAndService) {
        return partAndServiceRepository.save(partAndService);
    }

    public void delete(PartAndService partAndService) {
        partAndServiceRepository.delete(partAndService);
    }

    public Optional<PartAndService> update(Long id, PartAndService partAndService) {
        partAndService.setId(id);
        return Optional.of(partAndServiceRepository.save(partAndService));
    }
}
