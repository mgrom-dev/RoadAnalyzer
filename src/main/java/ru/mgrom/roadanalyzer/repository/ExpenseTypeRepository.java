package ru.mgrom.roadanalyzer.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import ru.mgrom.roadanalyzer.model.ExpenseType;
import ru.mgrom.roadanalyzer.service.DatabaseSchemaService;

@Repository
public class ExpenseTypeRepository {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DatabaseSchemaService databaseSchemaService;

    @Transactional
    public ExpenseType save(ExpenseType expenseType) {
        String databaseIdentifier = databaseSchemaService.getDatabaseIdentifierFromSession();

        String sql = "INSERT INTO " + databaseIdentifier + ".expense_type (description) VALUES (:description)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("description", expenseType.getDescription());

        query.executeUpdate();

        return expenseType;
    }

    public Optional<ExpenseType> findById(Long id) {
        String databaseIdentifier = databaseSchemaService.getDatabaseIdentifierFromSession();

        String sql = "SELECT * FROM " + databaseIdentifier + ".expense_type WHERE id = :id";
        Query query = entityManager.createNativeQuery(sql, ExpenseType.class);
        query.setParameter("id", id);

        List<?> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            ExpenseType expenseType = (ExpenseType) resultList.get(0);
            return Optional.of(expenseType);
        } else {
            return Optional.empty();
        }
    }

    public List<ExpenseType> findAll() {
        String databaseIdentifier = databaseSchemaService.getDatabaseIdentifierFromSession();

        String sql = "SELECT * FROM " + databaseIdentifier + ".expense_type";
        Query query = entityManager.createNativeQuery(sql, ExpenseType.class);

        List<ExpenseType> expenseTypes = new ArrayList<>();
        for (Object obj : query.getResultList()) {
            expenseTypes.add((ExpenseType) obj);
        }

        return expenseTypes;
    }

    public void deleteById(Long id) {
        String databaseIdentifier = databaseSchemaService.getDatabaseIdentifierFromSession();

        String sql = "DELETE FROM " + databaseIdentifier + ".expense_type WHERE id = :id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    public void delete(ExpenseType expenseType) {
        deleteById(expenseType.getId());
    }
}
