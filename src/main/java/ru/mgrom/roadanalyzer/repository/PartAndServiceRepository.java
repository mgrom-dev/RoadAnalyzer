package ru.mgrom.roadanalyzer.repository;

import ru.mgrom.roadanalyzer.model.PartAndService;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.Query;

@Repository
public class PartAndServiceRepository extends BaseRepository<PartAndService>
        implements GenericRepository<PartAndService> {

    public PartAndServiceRepository() {
        super(PartAndService.class, "part_and_service");
    }

    @Transactional
    public boolean save(PartAndService partAndService, String databaseIdentifier) {
        boolean isSaved = false;
        try {
            String sql = "INSERT INTO " + databaseIdentifier + "." + tableName
                    + "  (description, type) VALUES (:description, :type)";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("description", partAndService.getDescription());
            query.setParameter("type", partAndService.getType());
            query.executeUpdate();
            isSaved = true;
        } catch (Exception e) {
            System.out.println("error PartAndService: " + e.getMessage());
        }
        return isSaved;
    }

    public Optional<PartAndService> getByDescription(String description, String databaseIdentifier) {
        String sql = "SELECT * FROM " + databaseIdentifier + "." + tableName
                + " WHERE description = :description";
        Query query = createQuery(sql, PartAndService.class);
        query.setParameter("description", description);
        return Optional.ofNullable((PartAndService) query.getSingleResult());
    }
}