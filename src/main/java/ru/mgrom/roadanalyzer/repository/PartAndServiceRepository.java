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

    @Override
    @Transactional
    public boolean update(PartAndService partAndService, String databaseIdentifier) {
        boolean isUpdated = false;
        try {
            String sql = "UPDATE " + databaseIdentifier + "." + tableName
                    + " SET description = :description, type = :type WHERE id = :id";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("description", partAndService.getDescription());
            query.setParameter("type", partAndService.getType());
            query.setParameter("id", partAndService.getId());
            query.executeUpdate();
            isUpdated = true;
        } catch (Exception e) {
            System.out.println("error updating PartAndService: " + e.getMessage());
        }
        return isUpdated;
    }

    public Optional<PartAndService> getByDescription(String description, String databaseIdentifier) {
        String sql = "SELECT * FROM " + databaseIdentifier + "." + tableName
                + " WHERE description = :description";
        Query query = createQuery(sql, PartAndService.class);
        query.setParameter("description", description);
        return Optional.ofNullable((PartAndService) query.getSingleResult());
    }
}