package ru.mgrom.roadanalyzer.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.Query;
import ru.mgrom.roadanalyzer.model.ExpenseType;

@Repository
public class ExpenseTypeRepository extends BaseRepository<ExpenseType>
        implements GenericRepository<ExpenseType> {

    public ExpenseTypeRepository() {
        super(ExpenseType.class, "expense_type");
    }

    public Optional<ExpenseType> getByDescription(String description, String databaseIdentifier) {
        String sql = "SELECT * FROM " + databaseIdentifier + "." + tableName
                + " WHERE description = :description";
        Query query = createQuery(sql, ExpenseType.class);
        query.setParameter("description", description);
        return Optional.ofNullable((ExpenseType) query.getSingleResult());
    }
}
