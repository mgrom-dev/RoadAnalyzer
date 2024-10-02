package ru.mgrom.roadanalyzer.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import ru.mgrom.roadanalyzer.model.Spending;

@Repository
public class SpendingRepository extends BaseRepository<Spending> implements GenericRepository<Spending> {

    @Override
    protected String getTableName() {
        return "spending";
    }

    @Override
    protected Class<Spending> getEntityClass() {
        return Spending.class;
    }

    public List<Spending> findSpendingsByDateRange(LocalDate min, LocalDate max) {
        // execute query to the table in the needed schema
        String sql = "SELECT * FROM " + getDatabaseIdentifier() + "." + getTableName()
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

    @Override
    @Transactional
    public Spending save(Spending spending) {
        String sql = "INSERT INTO " + getDatabaseIdentifier() + "." + getTableName()
                + " (date, part_and_service_id, description, count, amount) VALUES (:date, :partAndServiceId, :description, :count, :amount)";
        Query query = createQuery(sql);
        query.setParameter("date", spending.getDate());
        query.setParameter("partAndServiceId", spending.getPartAndServiceId());
        query.setParameter("description", spending.getDescription());
        query.setParameter("count", spending.getCount());
        query.setParameter("amount", spending.getAmount());
        query.executeUpdate();

        String selectSql = "SELECT * FROM " + getDatabaseIdentifier()
                + "." + getTableName() + " WHERE part_and_service_id = :partAndServiceId ORDER BY id DESC LIMIT 1";
        Query selectQuery = createQuery(selectSql, Spending.class);
        selectQuery.setParameter("partAndServiceId", spending.getPartAndServiceId());

        Spending createdSpending = (Spending) selectQuery.getSingleResult();

        return createdSpending;
    }
}