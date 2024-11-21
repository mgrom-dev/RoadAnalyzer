package ru.mgrom.roadanalyzer.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ru.mgrom.roadanalyzer.repository.PartAndServiceRepository;
import ru.mgrom.roadanalyzer.model.PartAndService;

@Service
public class PartAndServiceService extends BaseService<PartAndService, PartAndServiceRepository>
        implements ServiceInterface<PartAndService> {

    public Optional<PartAndService> getByDescription(String description, String databaseIdentifier) {
        return repository.getByDescription(description, databaseIdentifier);
    }
}
