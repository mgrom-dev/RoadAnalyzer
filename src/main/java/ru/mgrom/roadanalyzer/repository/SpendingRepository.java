package ru.mgrom.roadanalyzer.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import ru.mgrom.roadanalyzer.dto.SpendingDTO;
import ru.mgrom.roadanalyzer.model.Spending;

@Repository
public class SpendingRepository extends BaseRepository<Spending> implements GenericRepository<Spending> {

    public SpendingRepository() {
        super(Spending.class, "spending");
    }

    public List<Spending> findSpendingsByDateRange(LocalDate min, LocalDate max, String databaseIdentifier) {
        // execute query to the table in the needed schema
        String sql = "SELECT * FROM " + databaseIdentifier + "." + tableName
                + " WHERE date BETWEEN :min AND :max";
        Query query = createQuery(sql, Spending.class);
        query.setParameter("min", min);
        query.setParameter("max", max);

        // convert query result to List<Spending>
        List<Spending> spendings = new ArrayList<>();
        for (Object obj : query.getResultList()) {
            spendings.add((Spending) obj);
        }

        return spendings;
    }

    public List<SpendingDTO> findSpendingsByDateRangeDTO(LocalDate min, LocalDate max, String databaseIdentifier) {
        String sql = "SELECT s.*, " +
                "p.description AS part_description, p.type AS part_type, e.description AS part_type_description " +
                "FROM " + databaseIdentifier + "." + tableName + " s " +
                "LEFT JOIN " + databaseIdentifier + ".part_and_service p ON s.part_and_service_id = p.id " +
                "LEFT JOIN " + databaseIdentifier + ".expense_type e ON p.type = e.id " +
                "WHERE s.date BETWEEN :min AND :max";

        Query query = createQuery(sql, SpendingDTO.class);
        query.setParameter("min", min);
        query.setParameter("max", max);

        List<SpendingDTO> spendings = new ArrayList<>();

        for (Object row : query.getResultList()) {
            spendings.add((SpendingDTO) row);
        }

        return spendings;
    }

    @Override
    @Transactional
    public boolean save(Spending spending, String databaseIdentifier) {
        boolean isSaved = false;
        try {
            String sql = "INSERT INTO " + databaseIdentifier + "." + tableName
                    + " (date, part_and_service_id, description, count, amount) VALUES (:date, :partAndServiceId, :description, :count, :amount)";
            Query query = createQuery(sql);
            query.setParameter("date", spending.getDate());
            query.setParameter("partAndServiceId", spending.getPartAndServiceId());
            query.setParameter("description", spending.getDescription());
            query.setParameter("count", spending.getCount());
            query.setParameter("amount", spending.getAmount());
            query.executeUpdate();
            isSaved = true;
        } catch (Exception e) {
            System.out.println("error PartAndService: " + e.getMessage());
        }
        return isSaved;
    }
}