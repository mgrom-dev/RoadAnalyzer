package ru.mgrom.roadanalyzer.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import ru.mgrom.roadanalyzer.model.Spending;
import ru.mgrom.roadanalyzer.service.DatabaseSchemaService;

@Repository
public class SpendingRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DatabaseSchemaService databaseSchemaService;

    public List<Spending> findSpendingsByDateRange(LocalDate min, LocalDate max) {
        // get scheme id for current user
        String databaseIdentifier = databaseSchemaService.getDatabaseIdentifierFromSession();

        // execute query to the table in the needed schema
        String sql = "SELECT * FROM " + databaseIdentifier + ".spending WHERE date BETWEEN :min AND :max";
        Query query = entityManager.createNativeQuery(sql, Spending.class);
        query.setParameter("min", min);
        query.setParameter("max", max);

        // convert query result to List<Spending>
        List<Spending> spendings = new ArrayList<>();
        for (Object obj : query.getResultList()) {
            spendings.add((Spending) obj);
        }

        return spendings;
    }

    @Transactional
    public Spending createSpending(Spending spending) {
        String databaseIdentifier = databaseSchemaService.getDatabaseIdentifierFromSession();

        String sql = "INSERT INTO " + databaseIdentifier
                + ".spending (date, part_and_service_id, description, count, amount) " +
                "VALUES (:date, :partAndServiceId, :description, :count, :amount)";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("date", spending.getDate());
        query.setParameter("partAndServiceId", spending.getPartAndServiceId());
        query.setParameter("description", spending.getDescription());
        query.setParameter("count", spending.getCount());
        query.setParameter("amount", spending.getAmount());

        query.executeUpdate();

        return spending;
    }
}