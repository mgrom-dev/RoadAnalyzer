package ru.mgrom.roadanalyzer.repository;

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
    public ExpenseType save(ExpenseType expenseType) {
        String sql = "INSERT INTO " + getDatabaseIdentifier() + "." + tableName
                + " (description) VALUES (:description)";
        Query query = createQuery(sql);
        query.setParameter("description", expenseType.getDescription());
        query.executeUpdate();

        // get created entity
        String selectSql = "SELECT * FROM " + getDatabaseIdentifier()
                + ".expense_type WHERE description = :description ORDER BY id DESC LIMIT 1";
        Query selectQuery = entityManager.createNativeQuery(selectSql, ExpenseType.class);
        selectQuery.setParameter("description", expenseType.getDescription());
        ExpenseType createdExpenseType = (ExpenseType) selectQuery.getSingleResult();

        return createdExpenseType;
    }
}
