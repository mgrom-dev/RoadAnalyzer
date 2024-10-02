package ru.mgrom.roadanalyzer.repository;

import ru.mgrom.roadanalyzer.model.PartAndService;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.Query;

@Repository
public class PartAndServiceRepository extends BaseRepository<PartAndService>
        implements GenericRepository<PartAndService> {

    @Override
    protected String getTableName() {
        return "part_and_service";
    }

    @Override
    protected Class<PartAndService> getEntityClass() {
        return PartAndService.class;
    }

    @Transactional
    public PartAndService save(PartAndService partAndService) {
        String sql = "INSERT INTO " + getDatabaseIdentifier() + "." + getTableName()
                + "  (description, type) VALUES (:description, :type)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("description", partAndService.getDescription());
        query.setParameter("type", partAndService.getType());

        query.executeUpdate();

        return partAndService;
    }
}