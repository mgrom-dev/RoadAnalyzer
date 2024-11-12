package ru.mgrom.roadanalyzer.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import ru.mgrom.roadanalyzer.model.ExpenseType;

@Repository
public class ExpenseTypeRepository extends BaseRepository<ExpenseType>
        implements GenericRepository<ExpenseType> {

    public ExpenseTypeRepository() {
        super(ExpenseType.class, "expense_type");
    }

    @Override
    @Transactional
    public boolean update(ExpenseType expenseType, String databaseIdentifier) {
        boolean isUpdated = false;
        try {
            String sql = "UPDATE " + databaseIdentifier + "." + tableName
                    + " SET description = :description WHERE id = :id";
            Query query = createQuery(sql);
            query.setParameter("description", expenseType.getDescription());
            query.setParameter("id", expenseType.getId());
            query.executeUpdate();
            isUpdated = true;
        } catch (Exception e) {
            System.out.println("error updating ExpenseType: " + e.getMessage());
        }
        return isUpdated;
    }

    public Optional<ExpenseType> getByDescription(String description, String databaseIdentifier) {
        String sql = "SELECT * FROM " + databaseIdentifier + "." + tableName
                + " WHERE description = :description";
        Query query = createQuery(sql, ExpenseType.class);
        query.setParameter("description", description);
        return Optional.ofNullable((ExpenseType) query.getSingleResult());
    }
}
