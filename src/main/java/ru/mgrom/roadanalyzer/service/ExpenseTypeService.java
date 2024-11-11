package ru.mgrom.roadanalyzer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ru.mgrom.roadanalyzer.model.ExpenseType;
import ru.mgrom.roadanalyzer.repository.ExpenseTypeRepository;

@Service
public class ExpenseTypeService {
    private final ExpenseTypeRepository expenseTypeRepository;

    public ExpenseTypeService(ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
    }

    public Optional<ExpenseType> getById(Long id, String databaseIdentifier) {
        return expenseTypeRepository.findById(id, databaseIdentifier);
    }

    public Optional<ExpenseType> getByDescription(String description, String databaseIdentifier) {
        return expenseTypeRepository.getByDescription(description, databaseIdentifier);
    }
    
    public List<ExpenseType> getAll(String databaseIdentifier) {
        return expenseTypeRepository.findAll(databaseIdentifier);
    }

    public boolean create(ExpenseType expenseType, String databaseIdentifier) {
        return expenseTypeRepository.save(expenseType, databaseIdentifier);
    }

    public void delete(ExpenseType expenseType, String databaseIdentifier) {
        expenseTypeRepository.delete(expenseType, databaseIdentifier);
    }

    public void delete(Long expenseTypeId, String databaseIdentifier) {
        expenseTypeRepository.deleteById(expenseTypeId, databaseIdentifier);
    }

    public boolean update(Long id, ExpenseType expenseType, String databaseIdentifier) {
        expenseType.setId(id);
        return expenseTypeRepository.save(expenseType, databaseIdentifier);
    }
}
