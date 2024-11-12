package ru.mgrom.roadanalyzer.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ru.mgrom.roadanalyzer.model.ExpenseType;
import ru.mgrom.roadanalyzer.repository.ExpenseTypeRepository;

@Service
public class ExpenseTypeService extends BaseService<ExpenseType, ExpenseTypeRepository> implements ServiceInterface<ExpenseType> {

    public Optional<ExpenseType> getByDescription(String description, String databaseIdentifier) {
        return repository.getByDescription(description, databaseIdentifier);
    }
    
}
