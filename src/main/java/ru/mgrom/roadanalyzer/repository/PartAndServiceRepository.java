package ru.mgrom.roadanalyzer.repository;

import ru.mgrom.roadanalyzer.model.PartAndService;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.Query;

@Repository
public class PartAndServiceRepository extends BaseRepository<PartAndService>
        implements GenericRepository<PartAndService> {

    public PartAndServiceRepository() {
        super(PartAndService.class, "part_and_service");
    }

    public Optional<PartAndService> getByDescription(String description, String databaseIdentifier) {
        String sql = "SELECT * FROM " + databaseIdentifier + "." + tableName
                + " WHERE description = :description";
        Query query = createQuery(sql, PartAndService.class);
        query.setParameter("description", description);
        return Optional.ofNullable((PartAndService) query.getSingleResult());
    }
}