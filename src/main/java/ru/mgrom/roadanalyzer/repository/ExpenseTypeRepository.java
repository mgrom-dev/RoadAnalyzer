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
    public boolean save(ExpenseType expenseType, String databaseIdentifier) {
        boolean isSaved = false;
        try {
            String sql = "INSERT INTO " + databaseIdentifier + "." + tableName
                    + " (description) VALUES (:description)";
            Query query = createQuery(sql);
            query.setParameter("description", expenseType.getDescription());
            query.executeUpdate();
            isSaved = true;
        } catch (Exception e) {
            System.out.println("error ExpenseType: " + e.getMessage());
        }
        return isSaved;
    }
}
